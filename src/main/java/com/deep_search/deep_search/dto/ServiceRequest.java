package com.deep_search.deep_search.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public class ServiceRequest {

    @NotBlank(message = "Service name is required")
    private String serviceName;

    private String description;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private BigDecimal price;

    private String duration;

    public ServiceRequest() {
    }

    public ServiceRequest(String serviceName, String description, BigDecimal price, String duration) {
        this.serviceName = serviceName;
        this.description = description;
        this.price = price;
        this.duration = duration;
    }

    // Getters and Setters
    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}

