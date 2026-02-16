package com.deep_search.deep_search.controller;

import com.deep_search.deep_search.dto.ServiceRequest;
import com.deep_search.deep_search.dto.ServiceResponse;
import com.deep_search.deep_search.service.ServiceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
public class ServiceController {

    private final ServiceService serviceService;

    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    // Create
    @PostMapping
    public ResponseEntity<ServiceResponse> createService(@Valid @RequestBody ServiceRequest request) {
        ServiceResponse response = serviceService.createService(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Read - Get All
    @GetMapping
    public ResponseEntity<List<ServiceResponse>> getAllServices() {
        List<ServiceResponse> services = serviceService.getAllServices();
        return ResponseEntity.ok(services);
    }

    // Read - Get By ID
    @GetMapping("/{serviceId}")
    public ResponseEntity<ServiceResponse> getServiceById(@PathVariable Integer serviceId) {
        ServiceResponse service = serviceService.getServiceById(serviceId);
        return ResponseEntity.ok(service);
    }

    // Read - Search
    @GetMapping("/search")
    public ResponseEntity<List<ServiceResponse>> searchServices(@RequestParam String name) {
        List<ServiceResponse> services = serviceService.searchServices(name);
        return ResponseEntity.ok(services);
    }

    // Update
    @PutMapping("/{serviceId}")
    public ResponseEntity<ServiceResponse> updateService(
            @PathVariable Integer serviceId,
            @Valid @RequestBody ServiceRequest request) {
        ServiceResponse response = serviceService.updateService(serviceId, request);
        return ResponseEntity.ok(response);
    }

    // Delete
    @DeleteMapping("/{serviceId}")
    public ResponseEntity<Void> deleteService(@PathVariable Integer serviceId) {
        serviceService.deleteService(serviceId);
        return ResponseEntity.noContent().build();
    }
}

