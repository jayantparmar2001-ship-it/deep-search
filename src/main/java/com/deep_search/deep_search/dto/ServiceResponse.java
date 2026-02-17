package com.deep_search.deep_search.dto;

import java.math.BigDecimal;
import java.util.List;

public class ServiceResponse {

    private Integer serviceId;
    private String serviceName;
    private String description;
    private BigDecimal price;
    private String duration;
    private List<ServiceTypeResponse> serviceTypes;

    public ServiceResponse() {
    }

    public ServiceResponse(Integer serviceId, String serviceName, String description, BigDecimal price, String duration, List<ServiceTypeResponse> serviceTypes) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.serviceTypes = serviceTypes;
    }

    // Getters and Setters
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

    public List<ServiceTypeResponse> getServiceTypes() {
        return serviceTypes;
    }

    public void setServiceTypes(List<ServiceTypeResponse> serviceTypes) {
        this.serviceTypes = serviceTypes;
    }
}


