package com.deep_search.deep_search.service;

import com.deep_search.deep_search.dto.AuthResponse;
import com.deep_search.deep_search.dto.LoginRequest;
import com.deep_search.deep_search.dto.RegisterRequest;
import com.deep_search.deep_search.entity.User;
import com.deep_search.deep_search.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Register a new user.
     */
    public AuthResponse register(RegisterRequest request) {
        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            return AuthResponse.error("Email already registered");
        }

        // Create and save user
        User user = new User(
                request.getName(),
                request.getEmail(),
                request.getPassword(), // In production, hash this with BCrypt!
                request.getPhone()
        );
        userRepository.save(user);

        // Generate a simple token (in production, use JWT)
        String token = UUID.randomUUID().toString();

        return AuthResponse.success(
                "Registration successful",
                token,
                user.getName(),
                user.getEmail()
        );
    }

    /**
     * Authenticate an existing user.
     */
    public AuthResponse login(LoginRequest request) {
        log.info("user request :{}",request.getEmail());
        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());

        if (optionalUser.isEmpty()) {
            return AuthResponse.error("User not found");
        }

        User user = optionalUser.get();

        // Check password (plain text comparison — in production, use BCrypt!)
        if (!user.getPassword().equals(request.getPassword())) {
            return AuthResponse.error("Invalid password");
        }

        // Generate a simple token (in production, use JWT)
        String token = UUID.randomUUID().toString();

        return AuthResponse.success(
                "Login successful",
                token,
                user.getName(),
                user.getEmail()
        );
    }
}

