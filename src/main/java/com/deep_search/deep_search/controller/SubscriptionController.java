package com.deep_search.deep_search.controller;

import com.deep_search.deep_search.dto.SubscriptionRequest;
import com.deep_search.deep_search.dto.SubscriptionResponse;
import com.deep_search.deep_search.service.SubscriptionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    // Create - Subscribe
    @PostMapping("/subscribe")
    public ResponseEntity<SubscriptionResponse> subscribe(@Valid @RequestBody SubscriptionRequest request) {
        SubscriptionResponse response = subscriptionService.subscribe(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Read - Get All
    @GetMapping
    public ResponseEntity<List<SubscriptionResponse>> getAllSubscriptions() {
        List<SubscriptionResponse> subscriptions = subscriptionService.getAllSubscriptions();
        return ResponseEntity.ok(subscriptions);
    }

    // Read - Get By ID
    @GetMapping("/{subscriptionId}")
    public ResponseEntity<SubscriptionResponse> getSubscriptionById(@PathVariable Integer subscriptionId) {
        SubscriptionResponse subscription = subscriptionService.getSubscriptionById(subscriptionId);
        return ResponseEntity.ok(subscription);
    }

    // Read - Get By User ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SubscriptionResponse>> getSubscriptionsByUserId(@PathVariable Integer userId) {
        List<SubscriptionResponse> subscriptions = subscriptionService.getSubscriptionsByUserId(userId);
        return ResponseEntity.ok(subscriptions);
    }

    // Read - Get Active Subscriptions by User
    @GetMapping("/user/{userId}/active")
    public ResponseEntity<List<SubscriptionResponse>> getActiveSubscriptionsByUserId(@PathVariable Integer userId) {
        List<SubscriptionResponse> subscriptions = subscriptionService.getActiveSubscriptionsByUserId(userId);
        return ResponseEntity.ok(subscriptions);
    }

    // Update - Renew
    @PostMapping("/{subscriptionId}/renew")
    public ResponseEntity<SubscriptionResponse> renewSubscription(
            @PathVariable Integer subscriptionId,
            @RequestParam(defaultValue = "1") Integer months) {
        SubscriptionResponse response = subscriptionService.renewSubscription(subscriptionId, months);
        return ResponseEntity.ok(response);
    }

    // Update - Cancel
    @PostMapping("/{subscriptionId}/cancel")
    public ResponseEntity<SubscriptionResponse> cancelSubscription(@PathVariable Integer subscriptionId) {
        SubscriptionResponse response = subscriptionService.cancelSubscription(subscriptionId);
        return ResponseEntity.ok(response);
    }

    // Delete
    @DeleteMapping("/{subscriptionId}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable Integer subscriptionId) {
        subscriptionService.deleteSubscription(subscriptionId);
        return ResponseEntity.noContent().build();
    }
}

