package com.deep_search.deep_search.controller;

import com.deep_search.deep_search.dto.LabourRequestResponse;
import com.deep_search.deep_search.dto.LabourRequestSubmitRequest;
import com.deep_search.deep_search.dto.UserLabourRequestsResponse;
import com.deep_search.deep_search.service.LabourRequestService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/labour-requests")
public class LabourRequestController {

    private final LabourRequestService labourRequestService;

    public LabourRequestController(LabourRequestService labourRequestService) {
        this.labourRequestService = labourRequestService;
    }

    /**
     * POST /api/labour-requests/submit
     * Submit labour request by selecting one of available services.
     */
    @PostMapping("/submit")
    public ResponseEntity<LabourRequestResponse> submitRequest(@Valid @RequestBody LabourRequestSubmitRequest request) {
        LabourRequestResponse response = labourRequestService.submitLabourRequest(request);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    /**
     * GET /api/labour-requests/my?token=...
     * Get logged-in user's labour requests.
     */
    @GetMapping("/my")
    public ResponseEntity<UserLabourRequestsResponse> getMyRequests(@RequestParam String token) {
        UserLabourRequestsResponse response = labourRequestService.getUserLabourRequests(token);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(401).body(response);
    }
}


