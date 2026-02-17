package com.deep_search.deep_search.service;

import com.deep_search.deep_search.dto.LabourRequestResponse;
import com.deep_search.deep_search.dto.LabourRequestSubmitRequest;
import com.deep_search.deep_search.dto.UserLabourRequestsResponse;
import com.deep_search.deep_search.entity.LabourRequest;
import com.deep_search.deep_search.entity.Service;
import com.deep_search.deep_search.entity.User;
import com.deep_search.deep_search.repository.LabourRequestRepository;
import com.deep_search.deep_search.repository.ServiceRepository;
import com.deep_search.deep_search.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class LabourRequestService {

    private static final Logger log = LoggerFactory.getLogger(LabourRequestService.class);

    private final LabourRequestRepository labourRequestRepository;
    private final UserRepository userRepository;
    private final ServiceRepository serviceRepository;
    private final AuthService authService;

    public LabourRequestService(
            LabourRequestRepository labourRequestRepository,
            UserRepository userRepository,
            ServiceRepository serviceRepository,
            AuthService authService
    ) {
        this.labourRequestRepository = labourRequestRepository;
        this.userRepository = userRepository;
        this.serviceRepository = serviceRepository;
        this.authService = authService;
    }

    @Transactional
    public LabourRequestResponse submitLabourRequest(LabourRequestSubmitRequest request) {
        try {
            if (!authService.isValidSession(request.getToken())) {
                return LabourRequestResponse.error("Session expired or invalid. Please login again.");
            }

            Optional<Integer> optionalUserId = authService.getUserIdFromToken(request.getToken());
            if (optionalUserId.isEmpty()) {
                return LabourRequestResponse.error("Invalid session. User not found.");
            }

            Integer userId = optionalUserId.get();
            Optional<User> optionalUser = userRepository.findById(userId);
            if (optionalUser.isEmpty()) {
                return LabourRequestResponse.error("User not found.");
            }

            Optional<Service> optionalService = serviceRepository.findById(request.getServiceId());
            if (optionalService.isEmpty()) {
                return LabourRequestResponse.error("Selected service not found.");
            }

            User user = optionalUser.get();
            Service selectedService = optionalService.get();

            LabourRequest labourRequest = new LabourRequest();
            labourRequest.setUserId(user.getUserId());
            labourRequest.setServiceId(selectedService.getServiceId());
            labourRequest.setUserName(user.getName());
            labourRequest.setUserEmail(user.getEmail());
            labourRequest.setUserPhone(user.getPhone());
            labourRequest.setAddress(request.getAddress());
            labourRequest.setPreferredDate(request.getPreferredDate());
            labourRequest.setNotes(request.getNotes());
            labourRequest.setStatus("NEW");

            LabourRequest savedRequest = labourRequestRepository.save(labourRequest);

            log.info("Labour request submitted. requestId={}, userId={}, serviceId={}",
                    savedRequest.getRequestId(), user.getUserId(), selectedService.getServiceId());

            return LabourRequestResponse.success(
                    "Labour request submitted successfully",
                    savedRequest.getRequestId(),
                    selectedService.getServiceId(),
                    selectedService.getServiceName(),
                    user.getEmail(),
                    savedRequest.getAddress(),
                    savedRequest.getPreferredDate(),
                    savedRequest.getStatus(),
                    savedRequest.getCreatedAt()
            );
        } catch (Exception e) {
            log.error("Failed to submit labour request: {}", e.getMessage(), e);
            return LabourRequestResponse.error("Failed to submit request: " + e.getMessage());
        }
    }

    public UserLabourRequestsResponse getUserLabourRequests(String token) {
        try {
            if (!authService.isValidSession(token)) {
                return UserLabourRequestsResponse.error("Session expired or invalid. Please login again.");
            }

            Optional<Integer> optionalUserId = authService.getUserIdFromToken(token);
            if (optionalUserId.isEmpty()) {
                return UserLabourRequestsResponse.error("Invalid session. User not found.");
            }

            List<LabourRequest> requests = labourRequestRepository
                    .findByUserIdOrderByCreatedAtDesc(optionalUserId.get());

            List<UserLabourRequestsResponse.LabourRequestItem> items = new ArrayList<>();
            for (LabourRequest request : requests) {
                UserLabourRequestsResponse.LabourRequestItem item = new UserLabourRequestsResponse.LabourRequestItem();
                item.setRequestId(request.getRequestId());
                item.setServiceId(request.getServiceId());
                item.setAddress(request.getAddress());
                item.setPreferredDate(request.getPreferredDate());
                item.setNotes(request.getNotes());
                item.setStatus(request.getStatus());
                item.setCreatedAt(request.getCreatedAt());

                serviceRepository.findById(request.getServiceId())
                        .ifPresent(service -> item.setServiceName(service.getServiceName()));

                items.add(item);
            }

            return UserLabourRequestsResponse.success("Labour requests fetched successfully", items);
        } catch (Exception e) {
            log.error("Failed to fetch labour requests: {}", e.getMessage(), e);
            return UserLabourRequestsResponse.error("Failed to fetch requests: " + e.getMessage());
        }
    }
}


