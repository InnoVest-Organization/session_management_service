package com.backend.sessionmanagementservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Converter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

@Entity
@Table(name = "invention_data")
public class Invention {
    @Id
    private Long invention_id;
    private LocalTime bidStartTime;
    private LocalTime bidEndTime;
    private LocalDate bidStartDate;
    private Integer isLive;
    private String productDescription;
    private String paymentPackage;

    @Column(name = "AOI")
    private String aoiString;

    // Transient field for the List<String> representation
    private transient List<String> aoi;

    // Getters and Setters
    public Long getId() {
        return invention_id;
    }

    public void setId(Long id) {
        this.invention_id = id;
    }

    public LocalTime getBidStartTime() {
        return bidStartTime;
    }

    public void setBidStartTime(LocalTime bidStartTime) {
        this.bidStartTime = bidStartTime;
    }

    public LocalTime getBidEndTime() {
        return bidEndTime;
    }

    public void setBidEndTime(LocalTime bidEndTime) {
        this.bidEndTime = bidEndTime;
    }

    public LocalDate getBidStartDate() {
        return bidStartDate;
    }

    public void setBidStartDate(LocalDate bidStartDate) {
        this.bidStartDate = bidStartDate;
    }

    public Integer getIsLive() {
        return isLive;
    }

    public void setIsLive(Integer isLive) {
        this.isLive = isLive;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getPaymentPackage() {
        return paymentPackage;
    }

    public void setPaymentPackage(String paymentPackage) {
        this.paymentPackage = paymentPackage;
    }

    public List<String> getAoi() {
        if (aoi == null && aoiString != null) {
            aoi = Arrays.asList(aoiString.split(","));
        }
        return aoi;
    }

    public void setAoi(List<String> aoi) {
        this.aoi = aoi;
        if (aoi != null) {
            this.aoiString = String.join(",", aoi);
        }
    }
}