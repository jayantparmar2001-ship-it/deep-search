package com.deep_search.deep_search.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.List;

public class ServiceTypeRequest {

    @NotBlank(message = "Type name is required")
    private String typeName;

    private String typeDescription;

    private String subscriptionPlan;

    @NotNull(message = "Type price is required")
    @Positive(message = "Type price must be positive")
    private BigDecimal typePrice;

    private List<String> photoUrls;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeDescription() {
        return typeDescription;
    }

    public void setTypeDescription(String typeDescription) {
        this.typeDescription = typeDescription;
    }

    public BigDecimal getTypePrice() {
        return typePrice;
    }

    public void setTypePrice(BigDecimal typePrice) {
        this.typePrice = typePrice;
    }

    public String getSubscriptionPlan() {
        return subscriptionPlan;
    }

    public void setSubscriptionPlan(String subscriptionPlan) {
        this.subscriptionPlan = subscriptionPlan;
    }

    public List<String> getPhotoUrls() {
        return photoUrls;
    }

    public void setPhotoUrls(List<String> photoUrls) {
        this.photoUrls = photoUrls;
    }
}

