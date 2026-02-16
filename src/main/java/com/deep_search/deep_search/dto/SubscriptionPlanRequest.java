package com.deep_search.deep_search.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public class SubscriptionPlanRequest {

    @NotBlank(message = "Plan name is required")
    private String planName;

    @NotNull(message = "Monthly price is required")
    @Positive(message = "Monthly price must be positive")
    private BigDecimal monthlyPrice;

    @NotNull(message = "Wash limit is required")
    @Positive(message = "Wash limit must be positive")
    private Integer washLimit;

    private String validityPeriod;

    public SubscriptionPlanRequest() {
    }

    public SubscriptionPlanRequest(String planName, BigDecimal monthlyPrice, Integer washLimit, String validityPeriod) {
        this.planName = planName;
        this.monthlyPrice = monthlyPrice;
        this.washLimit = washLimit;
        this.validityPeriod = validityPeriod;
    }

    // Getters and Setters
    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public BigDecimal getMonthlyPrice() {
        return monthlyPrice;
    }

    public void setMonthlyPrice(BigDecimal monthlyPrice) {
        this.monthlyPrice = monthlyPrice;
    }

    public Integer getWashLimit() {
        return washLimit;
    }

    public void setWashLimit(Integer washLimit) {
        this.washLimit = washLimit;
    }

    public String getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(String validityPeriod) {
        this.validityPeriod = validityPeriod;
    }
}

