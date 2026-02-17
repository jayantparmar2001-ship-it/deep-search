package com.deep_search.deep_search.service;

import com.deep_search.deep_search.dto.ServiceRequest;
import com.deep_search.deep_search.dto.ServiceResponse;
import com.deep_search.deep_search.dto.ServiceTypeRequest;
import com.deep_search.deep_search.dto.ServiceTypeResponse;
import com.deep_search.deep_search.entity.Service;
import com.deep_search.deep_search.entity.ServiceType;
import com.deep_search.deep_search.repository.ServiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class ServiceService {

    private static final Logger log = LoggerFactory.getLogger(ServiceService.class);
    private static final String PHOTO_DELIMITER = "|||";
    private final ServiceRepository serviceRepository;

    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    // Create
    @Transactional
    public ServiceResponse createService(ServiceRequest request) {
        log.info("Creating service: {}", request.getServiceName());
        validatePriceOrTypes(request);
        Service service = new Service(
                request.getServiceName(),
                request.getDescription(),
                request.getPrice(),
                request.getDuration()
        );
        applyServiceTypes(service, request.getServiceTypes());
        Service saved = serviceRepository.save(service);
        return toResponse(saved);
    }

    // Read - Get All
    public List<ServiceResponse> getAllServices() {
        log.info("Fetching all services");
        return serviceRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Read - Get By ID
    public ServiceResponse getServiceById(Integer serviceId) {
        log.info("Fetching service with ID: {}", serviceId);
        return serviceRepository.findById(serviceId)
                .map(this::toResponse)
                .orElseThrow(() -> new RuntimeException("Service not found with ID: " + serviceId));
    }

    // Read - Search by name
    public List<ServiceResponse> searchServices(String serviceName) {
        log.info("Searching services with name: {}", serviceName);
        return serviceRepository.findByServiceNameContainingIgnoreCase(serviceName).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Update
    @Transactional
    public ServiceResponse updateService(Integer serviceId, ServiceRequest request) {
        log.info("Updating service with ID: {}", serviceId);
        validatePriceOrTypes(request);
        Service service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found with ID: " + serviceId));

        service.setServiceName(request.getServiceName());
        service.setDescription(request.getDescription());
        service.setPrice(request.getPrice());
        service.setDuration(request.getDuration());
        applyServiceTypes(service, request.getServiceTypes());

        Service updated = serviceRepository.save(service);
        return toResponse(updated);
    }

    // Delete
    @Transactional
    public void deleteService(Integer serviceId) {
        log.info("Deleting service with ID: {}", serviceId);
        if (!serviceRepository.existsById(serviceId)) {
            throw new RuntimeException("Service not found with ID: " + serviceId);
        }
        serviceRepository.deleteById(serviceId);
    }

    private ServiceResponse toResponse(Service service) {
        return new ServiceResponse(
                service.getServiceId(),
                service.getServiceName(),
                service.getDescription(),
                service.getPrice(),
                service.getDuration(),
                toServiceTypeResponses(service.getServiceTypes())
        );
    }

    private void validatePriceOrTypes(ServiceRequest request) {
        boolean hasTopLevelPrice = request.getPrice() != null && request.getPrice().signum() > 0;
        boolean hasTypes = request.getServiceTypes() != null && !request.getServiceTypes().isEmpty();
        if (!hasTopLevelPrice && !hasTypes) {
            throw new RuntimeException("Either service price or at least one service type is required.");
        }
    }

    private void applyServiceTypes(Service service, List<ServiceTypeRequest> typeRequests) {
        service.getServiceTypes().clear();
        if (typeRequests == null || typeRequests.isEmpty()) {
            return;
        }

        for (ServiceTypeRequest typeRequest : typeRequests) {
            ServiceType type = new ServiceType();
            type.setService(service);
            type.setTypeName(typeRequest.getTypeName());
            type.setTypeDescription(typeRequest.getTypeDescription());
            type.setSubscriptionPlan(typeRequest.getSubscriptionPlan());
            type.setTypePrice(typeRequest.getTypePrice());
            type.setPhotoUrls(serializePhotoUrls(typeRequest.getPhotoUrls()));
            service.getServiceTypes().add(type);
        }
    }

    private List<ServiceTypeResponse> toServiceTypeResponses(List<ServiceType> serviceTypes) {
        if (serviceTypes == null || serviceTypes.isEmpty()) {
            return Collections.emptyList();
        }

        List<ServiceTypeResponse> responses = new ArrayList<>();
        for (ServiceType serviceType : serviceTypes) {
            responses.add(new ServiceTypeResponse(
                    serviceType.getServiceTypeId(),
                    serviceType.getTypeName(),
                    serviceType.getTypeDescription(),
                    serviceType.getSubscriptionPlan(),
                    serviceType.getTypePrice(),
                    deserializePhotoUrls(serviceType.getPhotoUrls())
            ));
        }
        return responses;
    }

    private String serializePhotoUrls(List<String> photoUrls) {
        if (photoUrls == null || photoUrls.isEmpty()) {
            return null;
        }
        return photoUrls.stream()
                .filter(url -> url != null && !url.isBlank())
                .map(String::trim)
                .collect(Collectors.joining(PHOTO_DELIMITER));
    }

    private List<String> deserializePhotoUrls(String serialized) {
        if (serialized == null || serialized.isBlank()) {
            return Collections.emptyList();
        }
        return List.of(serialized.split("\\Q" + PHOTO_DELIMITER + "\\E"));
    }
}


