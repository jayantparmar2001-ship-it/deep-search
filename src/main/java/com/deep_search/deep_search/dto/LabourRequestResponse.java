package com.deep_search.deep_search.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class LabourRequestResponse {

    private boolean success;
    private String message;
    private Long requestId;
    private Integer serviceId;
    private String serviceName;
    private String userEmail;
    private String address;
    private LocalDate preferredDate;
    private String status;
    private LocalDateTime createdAt;

    public LabourRequestResponse() {
    }

    public static LabourRequestResponse success(
            String message,
            Long requestId,
            Integer serviceId,
            String serviceName,
            String userEmail,
            String address,
            LocalDate preferredDate,
            String status,
            LocalDateTime createdAt
    ) {
        LabourRequestResponse response = new LabourRequestResponse();
        response.success = true;
        response.message = message;
        response.requestId = requestId;
        response.serviceId = serviceId;
        response.serviceName = serviceName;
        response.userEmail = userEmail;
        response.address = address;
        response.preferredDate = preferredDate;
        response.status = status;
        response.createdAt = createdAt;
        return response;
    }

    public static LabourRequestResponse error(String message) {
        LabourRequestResponse response = new LabourRequestResponse();
        response.success = false;
        response.message = message;
        return response;
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

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getPreferredDate() {
        return preferredDate;
    }

    public void setPreferredDate(LocalDate preferredDate) {
        this.preferredDate = preferredDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}


