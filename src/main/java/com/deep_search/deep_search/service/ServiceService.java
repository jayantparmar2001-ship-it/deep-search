package com.deep_search.deep_search.service;

import com.deep_search.deep_search.dto.ServiceRequest;
import com.deep_search.deep_search.dto.ServiceResponse;
import com.deep_search.deep_search.entity.Service;
import com.deep_search.deep_search.repository.ServiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class ServiceService {

    private static final Logger log = LoggerFactory.getLogger(ServiceService.class);
    private final ServiceRepository serviceRepository;

    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    // Create
    @Transactional
    public ServiceResponse createService(ServiceRequest request) {
        log.info("Creating service: {}", request.getServiceName());
        Service service = new Service(
                request.getServiceName(),
                request.getDescription(),
                request.getPrice(),
                request.getDuration()
        );
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
        Service service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found with ID: " + serviceId));

        service.setServiceName(request.getServiceName());
        service.setDescription(request.getDescription());
        service.setPrice(request.getPrice());
        service.setDuration(request.getDuration());

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
                service.getDuration()
        );
    }
}


