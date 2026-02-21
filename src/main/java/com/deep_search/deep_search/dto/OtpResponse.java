package com.deep_search.deep_search.dto;

public class OtpResponse {

    private boolean success;
    private String message;
    private String phoneNumber;

    public OtpResponse() {
    }

    public OtpResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public OtpResponse(boolean success, String message, String phoneNumber) {
        this.success = success;
        this.message = message;
        this.phoneNumber = phoneNumber;
    }

    public static OtpResponse success(String message) {
        return new OtpResponse(true, message);
    }

    public static OtpResponse success(String message, String phoneNumber) {
        return new OtpResponse(true, message, phoneNumber);
    }

    public static OtpResponse error(String message) {
        return new OtpResponse(false, message);
    }

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

