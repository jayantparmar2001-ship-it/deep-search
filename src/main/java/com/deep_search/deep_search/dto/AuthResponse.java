package com.deep_search.deep_search.dto;

public class AuthResponse {

    private boolean success;
    private String message;
    private String token;
    private String name;
    private String email;
    private String role;

    public AuthResponse() {
    }

    // Convenience factory methods

    public static AuthResponse success(String message, String token, String name, String email, String role) {
        AuthResponse response = new AuthResponse();
        response.success = true;
        response.message = message;
        response.token = token;
        response.name = name;
        response.email = email;
        response.role = role;
        return response;
    }

    public static AuthResponse error(String message) {
        AuthResponse response = new AuthResponse();
        response.success = false;
        response.message = message;
        return response;
    }

    // Getters and Setters

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}


