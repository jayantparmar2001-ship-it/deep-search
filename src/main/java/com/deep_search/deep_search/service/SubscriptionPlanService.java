package com.deep_search.deep_search.service;

import com.deep_search.deep_search.dto.SubscriptionPlanRequest;
import com.deep_search.deep_search.dto.SubscriptionPlanResponse;
import com.deep_search.deep_search.entity.SubscriptionPlan;
import com.deep_search.deep_search.repository.SubscriptionPlanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubscriptionPlanService {

    private static final Logger log = LoggerFactory.getLogger(SubscriptionPlanService.class);
    private final SubscriptionPlanRepository subscriptionPlanRepository;

    public SubscriptionPlanService(SubscriptionPlanRepository subscriptionPlanRepository) {
        this.subscriptionPlanRepository = subscriptionPlanRepository;
    }

    // Create
    @Transactional
    public SubscriptionPlanResponse createPlan(SubscriptionPlanRequest request) {
        log.info("Creating subscription plan: {}", request.getPlanName());
        SubscriptionPlan plan = new SubscriptionPlan(
                request.getPlanName(),
                request.getMonthlyPrice(),
                request.getWashLimit(),
                request.getValidityPeriod()
        );
        SubscriptionPlan saved = subscriptionPlanRepository.save(plan);
        return toResponse(saved);
    }

    // Read - Get All
    public List<SubscriptionPlanResponse> getAllPlans() {
        log.info("Fetching all subscription plans");
        return subscriptionPlanRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Read - Get Active Plans
    public List<SubscriptionPlanResponse> getActivePlans() {
        log.info("Fetching active subscription plans");
        return subscriptionPlanRepository.findByIsActiveTrue().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Read - Get By ID
    public SubscriptionPlanResponse getPlanById(Integer planId) {
        log.info("Fetching plan with ID: {}", planId);
        return subscriptionPlanRepository.findById(planId)
                .map(this::toResponse)
                .orElseThrow(() -> new RuntimeException("Subscription plan not found with ID: " + planId));
    }

    // Update
    @Transactional
    public SubscriptionPlanResponse updatePlan(Integer planId, SubscriptionPlanRequest request) {
        log.info("Updating plan with ID: {}", planId);
        SubscriptionPlan plan = subscriptionPlanRepository.findById(planId)
                .orElseThrow(() -> new RuntimeException("Subscription plan not found with ID: " + planId));

        plan.setPlanName(request.getPlanName());
        plan.setMonthlyPrice(request.getMonthlyPrice());
        plan.setWashLimit(request.getWashLimit());
        plan.setValidityPeriod(request.getValidityPeriod());

        SubscriptionPlan updated = subscriptionPlanRepository.save(plan);
        return toResponse(updated);
    }

    // Activate
    @Transactional
    public SubscriptionPlanResponse activatePlan(Integer planId) {
        log.info("Activating plan with ID: {}", planId);
        SubscriptionPlan plan = subscriptionPlanRepository.findById(planId)
                .orElseThrow(() -> new RuntimeException("Subscription plan not found with ID: " + planId));
        plan.setIsActive(true);
        SubscriptionPlan updated = subscriptionPlanRepository.save(plan);
        return toResponse(updated);
    }

    // Deactivate
    @Transactional
    public SubscriptionPlanResponse deactivatePlan(Integer planId) {
        log.info("Deactivating plan with ID: {}", planId);
        SubscriptionPlan plan = subscriptionPlanRepository.findById(planId)
                .orElseThrow(() -> new RuntimeException("Subscription plan not found with ID: " + planId));
        plan.setIsActive(false);
        SubscriptionPlan updated = subscriptionPlanRepository.save(plan);
        return toResponse(updated);
    }

    // Delete
    @Transactional
    public void deletePlan(Integer planId) {
        log.info("Deleting plan with ID: {}", planId);
        if (!subscriptionPlanRepository.existsById(planId)) {
            throw new RuntimeException("Subscription plan not found with ID: " + planId);
        }
        subscriptionPlanRepository.deleteById(planId);
    }

    private SubscriptionPlanResponse toResponse(SubscriptionPlan plan) {
        return new SubscriptionPlanResponse(
                plan.getPlanId(),
                plan.getPlanName(),
                plan.getMonthlyPrice(),
                plan.getWashLimit(),
                plan.getValidityPeriod(),
                plan.getIsActive()
        );
    }
}


