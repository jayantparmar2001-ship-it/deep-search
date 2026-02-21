package com.deep_search.deep_search.service;

import com.deep_search.deep_search.entity.Otp;
import com.deep_search.deep_search.entity.Session;
import com.deep_search.deep_search.entity.User;
import com.deep_search.deep_search.repository.OtpRepository;
import com.deep_search.deep_search.repository.SessionRepository;
import com.deep_search.deep_search.repository.UserRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class OtpService {

    private static final Logger log = LoggerFactory.getLogger(OtpService.class);
    private static final int OTP_LENGTH = 6;
    private static final int MAX_ATTEMPTS = 3;
    private static final int OTP_RESEND_COOLDOWN_MINUTES = 1;

    private final OtpRepository otpRepository;
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    @Value("${twilio.account.sid:}")
    private String twilioAccountSid;

    @Value("${twilio.auth.token:}")
    private String twilioAuthToken;

    @Value("${twilio.phone.number:}")
    private String twilioPhoneNumber;

    private boolean twilioInitialized = false;

    public OtpService(OtpRepository otpRepository, UserRepository userRepository, SessionRepository sessionRepository) {
        this.otpRepository = otpRepository;
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    /**
     * Initialize Twilio client (lazy initialization)
     */
    private void initializeTwilio() {
        if (!twilioInitialized && twilioAccountSid != null && !twilioAccountSid.isEmpty()
                && twilioAuthToken != null && !twilioAuthToken.isEmpty()) {
            try {
                Twilio.init(twilioAccountSid, twilioAuthToken);
                twilioInitialized = true;
                log.info("Twilio initialized successfully");
            } catch (Exception e) {
                log.error("Failed to initialize Twilio: {}", e.getMessage(), e);
            }
        } else if (twilioAccountSid == null || twilioAccountSid.isEmpty()) {
            log.warn("Twilio Account SID not configured. OTP SMS will not be sent.");
        }
    }

    /**
     * Generate a random 6-digit OTP code
     */
    private String generateOtpCode() {
        Random random = new Random();
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }

    /**
     * Normalize phone number to E.164 format
     */
    private String normalizePhoneNumber(String phoneNumber) {
        if (phoneNumber == null) {
            return null;
        }
        String normalized = phoneNumber.trim().replaceAll("[^+0-9]", "");
        // If it doesn't start with +, assume it's a local number and add +1 (US/Canada)
        // In production, you might want to use a phone number validation library
        if (!normalized.startsWith("+")) {
            normalized = "+91" + normalized;
        }
        return normalized;
    }

    /**
     * Send OTP to phone number
     */
    @Transactional
    public com.deep_search.deep_search.dto.OtpResponse sendOtp(String phoneNumber) {
        String normalizedPhone = normalizePhoneNumber(phoneNumber);
        if (normalizedPhone == null || normalizedPhone.length() < 10) {
            return com.deep_search.deep_search.dto.OtpResponse.error("Invalid phone number format");
        }

        log.info("Sending OTP to phone number: {}", normalizedPhone);

        // Check for recent OTP to prevent spam
        Optional<Otp> recentOtp = otpRepository.findLatestUnverifiedOtpByPhoneNumber(normalizedPhone);
        if (recentOtp.isPresent()) {
            Otp existingOtp = recentOtp.get();
            long minutesSinceCreation = java.time.Duration.between(
                    existingOtp.getCreatedAt(), java.time.LocalDateTime.now()).toMinutes();
            if (minutesSinceCreation < OTP_RESEND_COOLDOWN_MINUTES && !existingOtp.isExpired()) {
                return com.deep_search.deep_search.dto.OtpResponse.error(
                        "Please wait before requesting a new OTP. You can request a new OTP in " +
                                (OTP_RESEND_COOLDOWN_MINUTES - minutesSinceCreation) + " minute(s).");
            }
        }

        // Generate OTP code
        String otpCode = generateOtpCode();

        // Save OTP to database
        Otp otp = new Otp(normalizedPhone, otpCode);
        otpRepository.save(otp);

        // Send SMS via Twilio
        initializeTwilio();
        if (twilioInitialized && twilioPhoneNumber != null && !twilioPhoneNumber.isEmpty()) {
            try {
                Message message = Message.creator(
                        new PhoneNumber(normalizedPhone),
                        new PhoneNumber(twilioPhoneNumber),
                        "Your verification code is: " + otpCode + ". Valid for 5 minutes."
                ).create();

                log.info("OTP SMS sent successfully. Message SID: {}", message.getSid());
            } catch (Exception e) {
                log.error("Failed to send OTP SMS via Twilio: {}", e.getMessage(), e);
                // Still return success since OTP is saved in DB (can be verified manually)
                return com.deep_search.deep_search.dto.OtpResponse.success(
                        "OTP generated. SMS sending failed, but you can verify with code: " + otpCode,
                        normalizedPhone);
            }
        } else {
            log.warn("Twilio not configured. OTP generated but not sent via SMS. Code: {}", otpCode);
            // In development, return the OTP code for testing
            return com.deep_search.deep_search.dto.OtpResponse.success(
                    "OTP generated (SMS not configured). For testing, use code: " + otpCode,
                    normalizedPhone);
        }

        return com.deep_search.deep_search.dto.OtpResponse.success(
                "OTP sent successfully to " + normalizedPhone,
                normalizedPhone);
    }

    /**
     * Verify OTP and login user
     */
    @Transactional
    public com.deep_search.deep_search.dto.AuthResponse verifyOtp(String phoneNumber, String otpCode) {
        String normalizedPhone = normalizePhoneNumber(phoneNumber);
        if (normalizedPhone == null || normalizedPhone.length() < 10) {
            return com.deep_search.deep_search.dto.AuthResponse.error("Invalid phone number format");
        }

        log.info("Verifying OTP for phone number: {}", normalizedPhone);

        // Find the latest unverified OTP
        Optional<Otp> optionalOtp = otpRepository.findLatestUnverifiedOtpByPhoneNumber(normalizedPhone);
        if (optionalOtp.isEmpty()) {
            log.warn("No OTP found for phone number: {}", normalizedPhone);
            return com.deep_search.deep_search.dto.AuthResponse.error("No OTP found. Please request a new OTP.");
        }

        Otp otp = optionalOtp.get();

        // Check if OTP is expired
        if (otp.isExpired()) {
            log.warn("OTP expired for phone number: {}", normalizedPhone);
            return com.deep_search.deep_search.dto.AuthResponse.error("OTP has expired. Please request a new OTP.");
        }

        // Check attempts
        if (otp.getAttempts() >= MAX_ATTEMPTS) {
            log.warn("Max verification attempts reached for phone number: {}", normalizedPhone);
            return com.deep_search.deep_search.dto.AuthResponse.error(
                    "Maximum verification attempts reached. Please request a new OTP.");
        }

        // Verify OTP code
        if (!otp.getCode().equals(otpCode)) {
            otp.setAttempts(otp.getAttempts() + 1);
            otpRepository.save(otp);
            log.warn("Invalid OTP code for phone number: {}. Attempts: {}", normalizedPhone, otp.getAttempts());
            return com.deep_search.deep_search.dto.AuthResponse.error(
                    "Invalid OTP code. " + (MAX_ATTEMPTS - otp.getAttempts()) + " attempt(s) remaining.");
        }

        // OTP verified successfully
        otp.setIsVerified(true);
        otpRepository.save(otp);

        // Mark all OTPs for this phone as verified (cleanup)
        otpRepository.markAllAsVerifiedForPhone(normalizedPhone);

        // Find or create user by phone number
        Optional<User> optionalUser = userRepository.findByPhone(normalizedPhone);
        User user;
        if (optionalUser.isEmpty()) {
            // Create new user with phone number
            log.info("Creating new user for phone number: {}", normalizedPhone);
            user = new User();
            user.setName("User " + normalizedPhone.substring(Math.max(0, normalizedPhone.length() - 4)));
            user.setEmail(normalizedPhone + "@phone.local"); // Temporary email
            user.setPassword(UUID.randomUUID().toString()); // Random password (not used for phone login)
            user.setPhone(normalizedPhone);
            user.setRole("CUSTOMER"); // Default role
            userRepository.save(user);
        } else {
            user = optionalUser.get();
        }

        // Deactivate any existing active sessions for this user
        sessionRepository.deactivateAllUserSessions(user.getUserId());

        // Generate a unique token
        String token = UUID.randomUUID().toString();

        // Create and save session
        Session session = new Session(user.getUserId(), token);
        sessionRepository.save(session);

        log.info("OTP verification successful for user: {} (ID: {})", user.getPhone(), user.getUserId());

        com.deep_search.deep_search.dto.AuthResponse response = com.deep_search.deep_search.dto.AuthResponse.success(
                "Login successful",
                token,
                user.getName(),
                user.getEmail(),
                user.getRole());
        response.setProfileImageUrl(user.getProfileImageUrl());
        return response;
    }

    // Helper method to access SessionRepository (we'll need to add a getter in AuthService)
    // For now, we'll inject SessionRepository directly
}

