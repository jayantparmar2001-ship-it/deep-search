package com.deep_search.deep_search.controller;

import com.deep_search.deep_search.dto.LabourMappedServicesResponse;
import com.deep_search.deep_search.dto.LabourServiceMapRequest;
import com.deep_search.deep_search.dto.LabourServiceMappingResponse;
import com.deep_search.deep_search.service.LabourServiceMappingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/labour-services")
public class LabourServiceMappingController {

    private final LabourServiceMappingService mappingService;

    public LabourServiceMappingController(LabourServiceMappingService mappingService) {
        this.mappingService = mappingService;
    }

    /**
     * POST /api/labour-services/map
     * Maps a service to the logged in labour user.
     */
    @PostMapping("/map")
    public ResponseEntity<LabourServiceMappingResponse> mapService(@Valid @RequestBody LabourServiceMapRequest request) {
        LabourServiceMappingResponse response = mappingService.mapServiceToLabour(request);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    /**
     * GET /api/labour-services/my?token=...&search=...
     * Returns mapped services for the logged in labour user with optional search.
     */
    @GetMapping("/my")
    public ResponseEntity<LabourMappedServicesResponse> getMyMappedServices(
            @RequestParam String token,
            @RequestParam(required = false) String search
    ) {
        LabourMappedServicesResponse response = mappingService.getMappedServices(token, search);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(401).body(response);
    }
}


