package com.deep_search.deep_search.service;

import com.deep_search.deep_search.dto.AuthResponse;
import com.deep_search.deep_search.dto.LoginRequest;
import com.deep_search.deep_search.dto.RegisterRequest;
import com.deep_search.deep_search.entity.Session;
import com.deep_search.deep_search.entity.User;
import com.deep_search.deep_search.repository.SessionRepository;
import com.deep_search.deep_search.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private static final String ROLE_CUSTOMER = "CUSTOMER";
    private static final String ROLE_LABOUR = "LABOUR";

    public AuthService(UserRepository userRepository, SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    /**
     * Register a new user.
     */
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            return AuthResponse.error("Email already registered");
        }

        String requestedRole = request.getRole() == null ? ROLE_CUSTOMER : request.getRole().trim().toUpperCase();
        if (!ROLE_CUSTOMER.equals(requestedRole) && !ROLE_LABOUR.equals(requestedRole)) {
            return AuthResponse.error("Invalid role. Allowed values: CUSTOMER, LABOUR");
        }

        // Create and save user
        User user = new User(
                request.getName(),
                request.getEmail(),
                request.getPassword(), // In production, hash this with BCrypt!
                request.getPhone(),
                requestedRole
        );
        userRepository.save(user);

        // Generate a unique token
        String token = UUID.randomUUID().toString();

        // Create and save session for auto-login after registration
        Session session = new Session(user.getUserId(), token);
        sessionRepository.save(session);

        log.info("Registration successful for user: {} (ID: {})", user.getEmail(), user.getUserId());

        return AuthResponse.success(
                "Registration successful",
                token,
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }

    /**
     * Authenticate an existing user.
     */
    @Transactional
    public AuthResponse login(LoginRequest request) {
        log.info("Login attempt for email: {}", request.getEmail());
        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());

        // For security, don't reveal if user exists or not - use generic error message
        if (optionalUser.isEmpty()) {
            log.warn("Login failed: User not found for email: {}", request.getEmail());
            return AuthResponse.error("Invalid email or password");
        }

        User user = optionalUser.get();

        // Check password (plain text comparison — in production, use BCrypt!)
        if (!user.getPassword().equals(request.getPassword())) {
            log.warn("Login failed: Invalid password for email: {}", request.getEmail());
            return AuthResponse.error("Invalid email or password");
        }

        // Deactivate any existing active sessions for this user
        sessionRepository.deactivateAllUserSessions(user.getUserId());

        // Generate a unique token
        String token = UUID.randomUUID().toString();

        // Create and save session
        Session session = new Session(user.getUserId(), token);
        sessionRepository.save(session);

        log.info("Login successful for user: {} (ID: {})", user.getEmail(), user.getUserId());

        return AuthResponse.success(
                "Login successful",
                token,
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }

    /**
     * Logout a user by invalidating their session.
     */
    @Transactional
    public boolean logout(String token) {
        log.info("Logout attempt for token: {}", token != null ? token.substring(0, Math.min(10, token.length())) + "..." : "null");
        
        Optional<Session> optionalSession = sessionRepository.findByTokenAndIsActiveTrue(token);
        
        if (optionalSession.isEmpty()) {
            log.warn("Logout failed: Session not found or already inactive");
            return false;
        }

        Session session = optionalSession.get();
        session.setIsActive(false);
        sessionRepository.save(session);

        log.info("Logout successful for user ID: {}", session.getUserId());
        return true;
    }

    /**
     * Validate if a session token is valid and active.
     */
    public boolean isValidSession(String token) {
        if (token == null || token.isEmpty()) {
            return false;
        }

        Optional<Session> optionalSession = sessionRepository.findByTokenAndIsActiveTrue(token);
        
        if (optionalSession.isEmpty()) {
            return false;
        }

        Session session = optionalSession.get();
        
        // Check if session is expired
        if (session.isExpired()) {
            // Deactivate expired session
            session.setIsActive(false);
            sessionRepository.save(session);
            return false;
        }

        // Update last accessed time
        session.setLastAccessedAt(java.time.LocalDateTime.now());
        sessionRepository.save(session);
        
        return true;
    }

    /**
     * Get user ID from session token.
     */
    public Optional<Integer> getUserIdFromToken(String token) {
        Optional<Session> optionalSession = sessionRepository.findByTokenAndIsActiveTrue(token);
        return optionalSession.map(Session::getUserId);
    }
}

