package com.deep_search.deep_search.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "service_types")
public class ServiceType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_type_id")
    private Integer serviceTypeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", nullable = false)
    private Service service;

    @Column(name = "type_name", nullable = false, length = 255)
    private String typeName;

    @Column(name = "type_description", columnDefinition = "TEXT")
    private String typeDescription;

    @Column(name = "subscription_plan", length = 50)
    private String subscriptionPlan;

    @Column(name = "type_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal typePrice;

    @Column(name = "photo_urls", columnDefinition = "TEXT")
    private String photoUrls;

    public Integer getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(Integer serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
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

    public String getPhotoUrls() {
        return photoUrls;
    }

    public void setPhotoUrls(String photoUrls) {
        this.photoUrls = photoUrls;
    }
}

