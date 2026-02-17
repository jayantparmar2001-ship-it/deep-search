package com.deep_search.deep_search.dto;

import java.math.BigDecimal;
import java.util.List;

public class ServiceTypeResponse {

    private Integer serviceTypeId;
    private String typeName;
    private String typeDescription;
    private String subscriptionPlan;
    private BigDecimal typePrice;
    private List<String> photoUrls;

    public ServiceTypeResponse() {
    }

    public ServiceTypeResponse(Integer serviceTypeId, String typeName, String typeDescription, String subscriptionPlan, BigDecimal typePrice, List<String> photoUrls) {
        this.serviceTypeId = serviceTypeId;
        this.typeName = typeName;
        this.typeDescription = typeDescription;
        this.subscriptionPlan = subscriptionPlan;
        this.typePrice = typePrice;
        this.photoUrls = photoUrls;
    }

    public Integer getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(Integer serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

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

