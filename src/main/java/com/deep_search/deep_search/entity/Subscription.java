package com.deep_search.deep_search.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_id")
    private Integer subscriptionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", nullable = false)
    private SubscriptionPlan subscriptionPlan;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "status", length = 50, nullable = false)
    private String status = "ACTIVE"; // ACTIVE, EXPIRED, CANCELLED

    @Column(name = "washes_used", nullable = false)
    private Integer washesUsed = 0;

    public Subscription() {
    }

    public Subscription(User user, SubscriptionPlan subscriptionPlan, LocalDate startDate, LocalDate endDate) {
        this.user = user;
        this.subscriptionPlan = subscriptionPlan;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = "ACTIVE";
        this.washesUsed = 0;
    }

    // Getters and Setters
    public Integer getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(Integer subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SubscriptionPlan getSubscriptionPlan() {
        return subscriptionPlan;
    }

    public void setSubscriptionPlan(SubscriptionPlan subscriptionPlan) {
        this.subscriptionPlan = subscriptionPlan;
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
}

