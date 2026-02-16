package com.deep_search.deep_search.controller;

import com.deep_search.deep_search.dto.SubscriptionPlanRequest;
import com.deep_search.deep_search.dto.SubscriptionPlanResponse;
import com.deep_search.deep_search.service.SubscriptionPlanService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscription-plans")
public class SubscriptionPlanController {

    private final SubscriptionPlanService subscriptionPlanService;

    public SubscriptionPlanController(SubscriptionPlanService subscriptionPlanService) {
        this.subscriptionPlanService = subscriptionPlanService;
    }

    // Create
    @PostMapping
    public ResponseEntity<SubscriptionPlanResponse> createPlan(@Valid @RequestBody SubscriptionPlanRequest request) {
        SubscriptionPlanResponse response = subscriptionPlanService.createPlan(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Read - Get All
    @GetMapping
    public ResponseEntity<List<SubscriptionPlanResponse>> getAllPlans() {
        List<SubscriptionPlanResponse> plans = subscriptionPlanService.getAllPlans();
        return ResponseEntity.ok(plans);
    }

    // Read - Get Active Plans
    @GetMapping("/active")
    public ResponseEntity<List<SubscriptionPlanResponse>> getActivePlans() {
        List<SubscriptionPlanResponse> plans = subscriptionPlanService.getActivePlans();
        return ResponseEntity.ok(plans);
    }

    // Read - Get By ID
    @GetMapping("/{planId}")
    public ResponseEntity<SubscriptionPlanResponse> getPlanById(@PathVariable Integer planId) {
        SubscriptionPlanResponse plan = subscriptionPlanService.getPlanById(planId);
        return ResponseEntity.ok(plan);
    }

    // Update
    @PutMapping("/{planId}")
    public ResponseEntity<SubscriptionPlanResponse> updatePlan(
            @PathVariable Integer planId,
            @Valid @RequestBody SubscriptionPlanRequest request) {
        SubscriptionPlanResponse response = subscriptionPlanService.updatePlan(planId, request);
        return ResponseEntity.ok(response);
    }

    // Activate
    @PostMapping("/{planId}/activate")
    public ResponseEntity<SubscriptionPlanResponse> activatePlan(@PathVariable Integer planId) {
        SubscriptionPlanResponse response = subscriptionPlanService.activatePlan(planId);
        return ResponseEntity.ok(response);
    }

    // Deactivate
    @PostMapping("/{planId}/deactivate")
    public ResponseEntity<SubscriptionPlanResponse> deactivatePlan(@PathVariable Integer planId) {
        SubscriptionPlanResponse response = subscriptionPlanService.deactivatePlan(planId);
        return ResponseEntity.ok(response);
    }

    // Delete
    @DeleteMapping("/{planId}")
    public ResponseEntity<Void> deletePlan(@PathVariable Integer planId) {
        subscriptionPlanService.deletePlan(planId);
        return ResponseEntity.noContent().build();
    }
}

