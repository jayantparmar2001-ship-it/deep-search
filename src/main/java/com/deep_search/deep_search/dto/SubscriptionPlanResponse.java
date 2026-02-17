package com.deep_search.deep_search.dto;

import java.math.BigDecimal;

public class SubscriptionPlanResponse {

    private Integer planId;
    private String planName;
    private BigDecimal monthlyPrice;
    private Integer washLimit;
    private String validityPeriod;
    private Boolean isActive;

    public SubscriptionPlanResponse() {
    }

    public SubscriptionPlanResponse(Integer planId, String planName, BigDecimal monthlyPrice, Integer washLimit, String validityPeriod, Boolean isActive) {
        this.planId = planId;
        this.planName = planName;
        this.monthlyPrice = monthlyPrice;
        this.washLimit = washLimit;
        this.validityPeriod = validityPeriod;
        this.isActive = isActive;
    }

    // Getters and Setters
    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

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

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}


