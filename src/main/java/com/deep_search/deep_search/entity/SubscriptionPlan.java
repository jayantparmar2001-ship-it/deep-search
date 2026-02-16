package com.deep_search.deep_search.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "subscription_plans")
public class SubscriptionPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plan_id")
    private Integer planId;

    @Column(name = "plan_name", nullable = false, length = 255)
    private String planName;

    @Column(name = "monthly_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal monthlyPrice;

    @Column(name = "wash_limit", nullable = false)
    private Integer washLimit;

    @Column(name = "validity_period", length = 50)
    private String validityPeriod;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    public SubscriptionPlan() {
    }

    public SubscriptionPlan(String planName, BigDecimal monthlyPrice, Integer washLimit, String validityPeriod) {
        this.planName = planName;
        this.monthlyPrice = monthlyPrice;
        this.washLimit = washLimit;
        this.validityPeriod = validityPeriod;
        this.isActive = true;
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

