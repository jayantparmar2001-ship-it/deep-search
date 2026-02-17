package com.deep_search.deep_search.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class LabourServiceMapRequest {

    @NotBlank(message = "Token is required")
    private String token;

    @NotNull(message = "Service ID is required")
    private Integer serviceId;

    @Min(value = 0, message = "Experience years must be >= 0")
    @Max(value = 60, message = "Experience years must be <= 60")
    private Integer experienceYears;

    @Size(max = 2000, message = "Notes must be at most 2000 characters")
    private String notes;

    public LabourServiceMapRequest() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
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
}


