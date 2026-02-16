package com.deep_search.deep_search.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class SubscriptionRequest {

    @NotNull(message = "User ID is required")
    private Integer userId;

    @NotNull(message = "Plan ID is required")
    private Integer planId;

    private LocalDate startDate;

    private LocalDate endDate;

    public SubscriptionRequest() {
    }

    public SubscriptionRequest(Integer userId, Integer planId, LocalDate startDate, LocalDate endDate) {
        this.userId = userId;
        this.planId = planId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getters and Setters
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}

