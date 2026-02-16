package com.deep_search.deep_search.service;

import com.deep_search.deep_search.dto.SubscriptionRequest;
import com.deep_search.deep_search.dto.SubscriptionResponse;
import com.deep_search.deep_search.entity.Subscription;
import com.deep_search.deep_search.entity.SubscriptionPlan;
import com.deep_search.deep_search.entity.User;
import com.deep_search.deep_search.repository.SubscriptionPlanRepository;
import com.deep_search.deep_search.repository.SubscriptionRepository;
import com.deep_search.deep_search.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubscriptionService {

    private static final Logger log = LoggerFactory.getLogger(SubscriptionService.class);
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final SubscriptionPlanRepository subscriptionPlanRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository,
                               UserRepository userRepository,
                               SubscriptionPlanRepository subscriptionPlanRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.userRepository = userRepository;
        this.subscriptionPlanRepository = subscriptionPlanRepository;
    }

    // Create - Subscribe
    @Transactional
    public SubscriptionResponse subscribe(SubscriptionRequest request) {
        log.info("Creating subscription for user ID: {} with plan ID: {}", request.getUserId(), request.getPlanId());

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + request.getUserId()));

        SubscriptionPlan plan = subscriptionPlanRepository.findById(request.getPlanId())
                .orElseThrow(() -> new RuntimeException("Subscription plan not found with ID: " + request.getPlanId()));

        if (!plan.getIsActive()) {
            throw new RuntimeException("Subscription plan is not active");
        }

        LocalDate startDate = request.getStartDate() != null ? request.getStartDate() : LocalDate.now();
        LocalDate endDate = request.getEndDate() != null ? request.getEndDate() : startDate.plusMonths(1);

        Subscription subscription = new Subscription(user, plan, startDate, endDate);
        Subscription saved = subscriptionRepository.save(subscription);

        return toResponse(saved);
    }

    // Read - Get All
    public List<SubscriptionResponse> getAllSubscriptions() {
        log.info("Fetching all subscriptions");
        return subscriptionRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Read - Get By ID
    public SubscriptionResponse getSubscriptionById(Integer subscriptionId) {
        log.info("Fetching subscription with ID: {}", subscriptionId);
        return subscriptionRepository.findById(subscriptionId)
                .map(this::toResponse)
                .orElseThrow(() -> new RuntimeException("Subscription not found with ID: " + subscriptionId));
    }

    // Read - Get By User ID
    public List<SubscriptionResponse> getSubscriptionsByUserId(Integer userId) {
        log.info("Fetching subscriptions for user ID: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        return subscriptionRepository.findByUser(user).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Read - Get Active Subscriptions by User
    public List<SubscriptionResponse> getActiveSubscriptionsByUserId(Integer userId) {
        log.info("Fetching active subscriptions for user ID: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        return subscriptionRepository.findByUserAndStatus(user, "ACTIVE").stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Update - Renew
    @Transactional
    public SubscriptionResponse renewSubscription(Integer subscriptionId, Integer months) {
        log.info("Renewing subscription ID: {} for {} months", subscriptionId, months);
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new RuntimeException("Subscription not found with ID: " + subscriptionId));

        LocalDate newEndDate = subscription.getEndDate().plusMonths(months);
        subscription.setEndDate(newEndDate);
        subscription.setStatus("ACTIVE");
        subscription.setWashesUsed(0); // Reset wash count on renewal

        Subscription updated = subscriptionRepository.save(subscription);
        return toResponse(updated);
    }

    // Update - Cancel
    @Transactional
    public SubscriptionResponse cancelSubscription(Integer subscriptionId) {
        log.info("Cancelling subscription ID: {}", subscriptionId);
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new RuntimeException("Subscription not found with ID: " + subscriptionId));

        subscription.setStatus("CANCELLED");
        Subscription updated = subscriptionRepository.save(subscription);
        return toResponse(updated);
    }

    // Delete
    @Transactional
    public void deleteSubscription(Integer subscriptionId) {
        log.info("Deleting subscription ID: {}", subscriptionId);
        if (!subscriptionRepository.existsById(subscriptionId)) {
            throw new RuntimeException("Subscription not found with ID: " + subscriptionId);
        }
        subscriptionRepository.deleteById(subscriptionId);
    }

    private SubscriptionResponse toResponse(Subscription subscription) {
        return new SubscriptionResponse(
                subscription.getSubscriptionId(),
                subscription.getUser().getUserId(),
                subscription.getUser().getName(),
                subscription.getSubscriptionPlan().getPlanId(),
                subscription.getSubscriptionPlan().getPlanName(),
                subscription.getStartDate(),
                subscription.getEndDate(),
                subscription.getStatus(),
                subscription.getWashesUsed(),
                subscription.getSubscriptionPlan().getWashLimit()
        );
    }
}

