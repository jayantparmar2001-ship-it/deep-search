package com.deep_search.deep_search.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentResponse {

    private Integer paymentId;
    private Integer userId;
    private String userName;
    private Integer subscriptionId;
    private BigDecimal amount;
    private LocalDateTime paymentDate;
    private String paymentMethod;
    private String paymentStatus;
    private String transactionId;

    public PaymentResponse() {
    }

    public PaymentResponse(Integer paymentId, Integer userId, String userName, Integer subscriptionId, BigDecimal amount, LocalDateTime paymentDate, String paymentMethod, String paymentStatus, String transactionId) {
        this.paymentId = paymentId;
        this.userId = userId;
        this.userName = userName;
        this.subscriptionId = subscriptionId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.transactionId = transactionId;
    }

    // Getters and Setters
    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
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

    public Integer getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(Integer subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}

