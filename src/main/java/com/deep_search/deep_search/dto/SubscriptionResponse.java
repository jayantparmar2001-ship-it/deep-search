package com.deep_search.deep_search.dto;

import java.time.LocalDate;

public class SubscriptionResponse {

    private Integer subscriptionId;
    private Integer userId;
    private String userName;
    private Integer planId;
    private String planName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private Integer washesUsed;
    private Integer washLimit;

    public SubscriptionResponse() {
    }

    public SubscriptionResponse(Integer subscriptionId, Integer userId, String userName, Integer planId, String planName, LocalDate startDate, LocalDate endDate, String status, Integer washesUsed, Integer washLimit) {
        this.subscriptionId = subscriptionId;
        this.userId = userId;
        this.userName = userName;
        this.planId = planId;
        this.planName = planName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.washesUsed = washesUsed;
        this.washLimit = washLimit;
    }

    // Getters and Setters
    public Integer getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(Integer subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getWashesUsed() {
        return washesUsed;
    }

    public void setWashesUsed(Integer washesUsed) {
        this.washesUsed = washesUsed;
    }

    public Integer getWashLimit() {
        return washLimit;
    }

    public void setWashLimit(Integer washLimit) {
        this.washLimit = washLimit;
    }
}


