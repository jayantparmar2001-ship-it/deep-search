package com.deep_search.deep_search.dto;

import java.time.LocalDateTime;

public class CustomerQueryResponse {

    private boolean success;
    private String message;
    private Long queryId;
    private String email;
    private String subject;
    private LocalDateTime createdAt;
    private String status;

    public CustomerQueryResponse() {
    }

    public static CustomerQueryResponse success(String message, Long queryId, String email, String subject, LocalDateTime createdAt, String status) {
        CustomerQueryResponse response = new CustomerQueryResponse();
        response.success = true;
        response.message = message;
        response.queryId = queryId;
        response.email = email;
        response.subject = subject;
        response.createdAt = createdAt;
        response.status = status;
        return response;
    }

    public static CustomerQueryResponse error(String message) {
        CustomerQueryResponse response = new CustomerQueryResponse();
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

    public Long getQueryId() {
        return queryId;
    }

    public void setQueryId(Long queryId) {
        this.queryId = queryId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}


