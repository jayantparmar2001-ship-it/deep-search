package com.deep_search.deep_search.service;

import com.deep_search.deep_search.dto.LabourMappedServicesResponse;
import com.deep_search.deep_search.dto.LabourServiceMapRequest;
import com.deep_search.deep_search.dto.LabourServiceMappingResponse;
import com.deep_search.deep_search.entity.LabourServiceMapping;
import com.deep_search.deep_search.entity.Service;
import com.deep_search.deep_search.entity.User;
import com.deep_search.deep_search.repository.LabourServiceMappingRepository;
import com.deep_search.deep_search.repository.ServiceRepository;
import com.deep_search.deep_search.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class LabourServiceMappingService {

    private static final Logger log = LoggerFactory.getLogger(LabourServiceMappingService.class);
    private static final String ROLE_LABOUR = "LABOUR";
    private static final String ROLE_CUSTOMER = "CUSTOMER";

    private final LabourServiceMappingRepository mappingRepository;
    private final ServiceRepository serviceRepository;
    private final UserRepository userRepository;
    private final AuthService authService;

    public LabourServiceMappingService(
            LabourServiceMappingRepository mappingRepository,
            ServiceRepository serviceRepository,
            UserRepository userRepository,
            AuthService authService
    ) {
        this.mappingRepository = mappingRepository;
        this.serviceRepository = serviceRepository;
        this.userRepository = userRepository;
        this.authService = authService;
    }

    @Transactional
    public LabourServiceMappingResponse mapServiceToLabour(LabourServiceMapRequest request) {
        try {
            if (!authService.isValidSession(request.getToken())) {
                return LabourServiceMappingResponse.error("Session expired or invalid. Please login again.");
            }

            Optional<Integer> optionalUserId = authService.getUserIdFromToken(request.getToken());
            if (optionalUserId.isEmpty()) {
                return LabourServiceMappingResponse.error("Invalid session.");
            }

            Optional<User> optionalUser = userRepository.findById(optionalUserId.get());
            if (optionalUser.isEmpty()) {
                return LabourServiceMappingResponse.error("User not found.");
            }

            User user = optionalUser.get();
            if (!ROLE_LABOUR.equalsIgnoreCase(user.getRole())) {
                return LabourServiceMappingResponse.error("Only labour users can map services.");
            }

            Optional<Service> optionalService = serviceRepository.findById(request.getServiceId());
            if (optionalService.isEmpty()) {
                return LabourServiceMappingResponse.error("Service not found.");
            }

            if (mappingRepository.existsByLabourUserIdAndServiceIdAndIsActiveTrue(user.getUserId(), request.getServiceId())) {
                return LabourServiceMappingResponse.error("Service already mapped for this labour user.");
            }

            LabourServiceMapping mapping = new LabourServiceMapping();
            mapping.setLabourUserId(user.getUserId());
            mapping.setServiceId(request.getServiceId());
            mapping.setExperienceYears(request.getExperienceYears());
            mapping.setNotes(request.getNotes());
            mapping.setIsActive(true);

            LabourServiceMapping saved = mappingRepository.save(mapping);
            Service service = optionalService.get();

            return LabourServiceMappingResponse.success(
                    "Service mapped successfully",
                    saved.getMappingId(),
                    service.getServiceId(),
                    service.getServiceName(),
                    saved.getExperienceYears(),
                    saved.getNotes(),
                    saved.getCreatedAt()
            );
        } catch (Exception e) {
            log.error("Failed to map service: {}", e.getMessage(), e);
            return LabourServiceMappingResponse.error("Failed to map service: " + e.getMessage());
        }
    }

    public LabourMappedServicesResponse getMappedServices(String token, String search) {
        try {
            if (!authService.isValidSession(token)) {
                return LabourMappedServicesResponse.error("Session expired or invalid. Please login again.");
            }

            Optional<Integer> optionalUserId = authService.getUserIdFromToken(token);
            if (optionalUserId.isEmpty()) {
                return LabourMappedServicesResponse.error("Invalid session.");
            }

            Optional<User> optionalUser = userRepository.findById(optionalUserId.get());
            if (optionalUser.isEmpty() || !ROLE_LABOUR.equalsIgnoreCase(optionalUser.get().getRole())) {
                return LabourMappedServicesResponse.error("Only labour users can access mapped services.");
            }

            List<LabourServiceMapping> mappings = mappingRepository
                    .findByLabourUserIdAndIsActiveTrueOrderByCreatedAtDesc(optionalUserId.get());

            String normalizedSearch = search == null ? "" : search.trim().toLowerCase();
            List<LabourMappedServicesResponse.MappedServiceItem> items = new ArrayList<>();

            for (LabourServiceMapping mapping : mappings) {
                Optional<Service> optionalService = serviceRepository.findById(mapping.getServiceId());
                if (optionalService.isEmpty()) {
                    continue;
                }

                Service service = optionalService.get();
                if (!normalizedSearch.isBlank()
                        && !service.getServiceName().toLowerCase().contains(normalizedSearch)
                        && (service.getDescription() == null
                        || !service.getDescription().toLowerCase().contains(normalizedSearch))) {
                    continue;
                }

                LabourMappedServicesResponse.MappedServiceItem item =
                        new LabourMappedServicesResponse.MappedServiceItem();
                item.setMappingId(mapping.getMappingId());
                item.setLabourUserId(mapping.getLabourUserId());
                item.setLabourName(optionalUser.get().getName());
                item.setLabourEmail(optionalUser.get().getEmail());
                item.setServiceId(service.getServiceId());
                item.setServiceName(service.getServiceName());
                item.setDescription(service.getDescription());
                item.setExperienceYears(mapping.getExperienceYears());
                item.setNotes(mapping.getNotes());
                item.setCreatedAt(mapping.getCreatedAt());
                items.add(item);
            }

            return LabourMappedServicesResponse.success("Mapped services fetched successfully", items);
        } catch (Exception e) {
            log.error("Failed to fetch mapped services: {}", e.getMessage(), e);
            return LabourMappedServicesResponse.error("Failed to fetch mapped services: " + e.getMessage());
        }
    }

    public LabourMappedServicesResponse getAvailableMappedServices(String token, String search) {
        try {
            if (!authService.isValidSession(token)) {
                return LabourMappedServicesResponse.error("Session expired or invalid. Please login again.");
            }

            Optional<String> optionalRole = authService.getUserRoleFromToken(token);
            if (optionalRole.isEmpty() || !ROLE_CUSTOMER.equalsIgnoreCase(optionalRole.get())) {
                return LabourMappedServicesResponse.error("Only customer users can access available services.");
            }

            String normalizedSearch = search == null ? "" : search.trim().toLowerCase();
            List<LabourServiceMapping> mappings = mappingRepository.findByIsActiveTrueOrderByCreatedAtDesc();
            List<LabourMappedServicesResponse.MappedServiceItem> items = new ArrayList<>();

            for (LabourServiceMapping mapping : mappings) {
                Optional<Service> optionalService = serviceRepository.findById(mapping.getServiceId());
                Optional<User> optionalLabour = userRepository.findById(mapping.getLabourUserId());
                if (optionalService.isEmpty() || optionalLabour.isEmpty()) {
                    continue;
                }

                Service service = optionalService.get();
                User labour = optionalLabour.get();
                if (!ROLE_LABOUR.equalsIgnoreCase(labour.getRole())) {
                    continue;
                }

                boolean serviceMatches = service.getServiceName().toLowerCase().contains(normalizedSearch)
                        || (service.getDescription() != null
                        && service.getDescription().toLowerCase().contains(normalizedSearch));
                boolean labourMatches = (labour.getName() != null && labour.getName().toLowerCase().contains(normalizedSearch))
                        || (labour.getEmail() != null && labour.getEmail().toLowerCase().contains(normalizedSearch));

                if (!normalizedSearch.isBlank() && !serviceMatches && !labourMatches) {
                    continue;
                }

                LabourMappedServicesResponse.MappedServiceItem item = new LabourMappedServicesResponse.MappedServiceItem();
                item.setMappingId(mapping.getMappingId());
                item.setLabourUserId(mapping.getLabourUserId());
                item.setLabourName(labour.getName());
                item.setLabourEmail(labour.getEmail());
                item.setServiceId(service.getServiceId());
                item.setServiceName(service.getServiceName());
                item.setDescription(service.getDescription());
                item.setExperienceYears(mapping.getExperienceYears());
                item.setNotes(mapping.getNotes());
                item.setCreatedAt(mapping.getCreatedAt());
                items.add(item);
            }

            return LabourMappedServicesResponse.success("Available labour services fetched successfully", items);
        } catch (Exception e) {
            log.error("Failed to fetch available labour services: {}", e.getMessage(), e);
            return LabourMappedServicesResponse.error("Failed to fetch available labour services: " + e.getMessage());
        }
    }
}


