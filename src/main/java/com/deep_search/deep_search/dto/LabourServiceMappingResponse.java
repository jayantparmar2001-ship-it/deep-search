package com.deep_search.deep_search.dto;

import java.time.LocalDateTime;

public class LabourServiceMappingResponse {

    private boolean success;
    private String message;
    private Long mappingId;
    private Integer serviceId;
    private String serviceName;
    private Integer experienceYears;
    private String notes;
    private LocalDateTime createdAt;

    public LabourServiceMappingResponse() {
    }

    public static LabourServiceMappingResponse success(
            String message,
            Long mappingId,
            Integer serviceId,
            String serviceName,
            Integer experienceYears,
            String notes,
            LocalDateTime createdAt
    ) {
        LabourServiceMappingResponse response = new LabourServiceMappingResponse();
        response.success = true;
        response.message = message;
        response.mappingId = mappingId;
        response.serviceId = serviceId;
        response.serviceName = serviceName;
        response.experienceYears = experienceYears;
        response.notes = notes;
        response.createdAt = createdAt;
        return response;
    }

    public static LabourServiceMappingResponse error(String message) {
        LabourServiceMappingResponse response = new LabourServiceMappingResponse();
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

    public Long getMappingId() {
        return mappingId;
    }

    public void setMappingId(Long mappingId) {
        this.mappingId = mappingId;
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

    public Integer getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(Integer experienceYears) {
        this.experienceYears = experienceYears;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}


