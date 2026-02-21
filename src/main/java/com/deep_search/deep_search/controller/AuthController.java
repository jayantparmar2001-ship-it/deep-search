package com.deep_search.deep_search.controller;

import com.deep_search.deep_search.dto.AuthResponse;
import com.deep_search.deep_search.dto.LoginRequest;
import com.deep_search.deep_search.dto.LogoutRequest;
import com.deep_search.deep_search.dto.OtpResponse;
import com.deep_search.deep_search.dto.RegisterRequest;
import com.deep_search.deep_search.dto.SendOtpRequest;
import com.deep_search.deep_search.dto.VerifyOtpRequest;
import com.deep_search.deep_search.service.AuthService;
import com.deep_search.deep_search.service.OtpService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final OtpService otpService;

    public AuthController(AuthService authService, OtpService otpService) {
        this.authService = authService;
        this.otpService = otpService;
    }

    /**
     * POST /api/auth/login
     * Body: { "email": "...", "password": "..." }
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body(response);
        }
    }

    /**
     * POST /api/auth/register
     * Body: { "name": "...", "email": "...", "password": "..." }
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(409).body(response);
        }
    }

    /**
     * POST /api/auth/logout
     * Logout a user by invalidating their session
     * Body: { "token": "..." }
     */
    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout(@Valid @RequestBody LogoutRequest request) {
        Map<String, Object> response = new HashMap<>();
        boolean success = authService.logout(request.getToken());
        
        if (success) {
            response.put("success", true);
            response.put("message", "Logout successful");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Invalid or expired session");
            return ResponseEntity.status(401).body(response);
        }
    }

    /**
     * POST /api/auth/send-otp
     * Send OTP to phone number via SMS
     * Body: { "phoneNumber": "+1234567890" }
     */
    @PostMapping("/send-otp")
    public ResponseEntity<OtpResponse> sendOtp(@Valid @RequestBody SendOtpRequest request) {
        OtpResponse response = otpService.sendOtp(request.getPhoneNumber());
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(400).body(response);
        }
    }

    /**
     * POST /api/auth/verify-otp
     * Verify OTP and login user
     * Body: { "phoneNumber": "+1234567890", "otpCode": "123456" }
     */
    @PostMapping("/verify-otp")
    public ResponseEntity<AuthResponse> verifyOtp(@Valid @RequestBody VerifyOtpRequest request) {
        AuthResponse response = otpService.verifyOtp(request.getPhoneNumber(), request.getOtpCode());
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body(response);
        }
    }

    /**
     * GET /api/auth/health
     * Simple health check endpoint.
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("deep-search auth service is running!");
    }
}


