package com.deep_search.deep_search.controller;

import com.deep_search.deep_search.dto.CustomerQueryRequest;
import com.deep_search.deep_search.dto.CustomerQueryResponse;
import com.deep_search.deep_search.service.CustomerQueryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/queries")
public class CustomerQueryController {

    private final CustomerQueryService customerQueryService;

    public CustomerQueryController(CustomerQueryService customerQueryService) {
        this.customerQueryService = customerQueryService;
    }

    /**
     * POST /api/queries/submit
     * Submit a customer query/inquiry
     * Body: { "email": "...", "subject": "...", "message": "..." }
     */
    @PostMapping("/submit")
    public ResponseEntity<CustomerQueryResponse> submitQuery(@Valid @RequestBody CustomerQueryRequest request) {
        CustomerQueryResponse response = customerQueryService.submitQuery(request);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(500).body(response);
        }
    }
}


