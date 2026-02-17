package com.deep_search.deep_search.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

public class ServiceRequest {

    @NotBlank(message = "Service name is required")
    private String serviceName;

    private String description;

    private BigDecimal price;

    private String duration;

    @Valid
    private List<ServiceTypeRequest> serviceTypes;

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

    public List<ServiceTypeRequest> getServiceTypes() {
        return serviceTypes;
    }

    public void setServiceTypes(List<ServiceTypeRequest> serviceTypes) {
        this.serviceTypes = serviceTypes;
    }
}


