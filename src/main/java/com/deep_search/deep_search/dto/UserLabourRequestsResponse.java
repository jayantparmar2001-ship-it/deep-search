package com.deep_search.deep_search.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserLabourRequestsResponse {

    private boolean success;
    private String message;
    private List<LabourRequestItem> requests;

    public UserLabourRequestsResponse() {
        this.requests = new ArrayList<>();
    }

    public static UserLabourRequestsResponse success(String message, List<LabourRequestItem> requests) {
        UserLabourRequestsResponse response = new UserLabourRequestsResponse();
        response.success = true;
        response.message = message;
        response.requests = requests;
        return response;
    }

    public static UserLabourRequestsResponse error(String message) {
        UserLabourRequestsResponse response = new UserLabourRequestsResponse();
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

    public List<LabourRequestItem> getRequests() {
        return requests;
    }

    public void setRequests(List<LabourRequestItem> requests) {
        this.requests = requests;
    }

    public static class LabourRequestItem {
        private Long requestId;
        private Integer serviceId;
        private String serviceName;
        private String address;
        private LocalDate preferredDate;
        private String notes;
        private String status;
        private LocalDateTime createdAt;

        public LabourRequestItem() {
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

        public String getNotes() {
            return notes;
        }

        public void setNotes(String notes) {
            this.notes = notes;
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
}


