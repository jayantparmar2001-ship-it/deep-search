package com.deep_search.deep_search.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LabourMappedServicesResponse {

    private boolean success;
    private String message;
    private List<MappedServiceItem> services;

    public LabourMappedServicesResponse() {
        this.services = new ArrayList<>();
    }

    public static LabourMappedServicesResponse success(String message, List<MappedServiceItem> services) {
        LabourMappedServicesResponse response = new LabourMappedServicesResponse();
        response.success = true;
        response.message = message;
        response.services = services;
        return response;
    }

    public static LabourMappedServicesResponse error(String message) {
        LabourMappedServicesResponse response = new LabourMappedServicesResponse();
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

    public List<MappedServiceItem> getServices() {
        return services;
    }

    public void setServices(List<MappedServiceItem> services) {
        this.services = services;
    }

    public static class MappedServiceItem {
        private Long mappingId;
        private Integer serviceId;
        private String serviceName;
        private String description;
        private Integer experienceYears;
        private String notes;
        private LocalDateTime createdAt;

        public MappedServiceItem() {
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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
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
}


