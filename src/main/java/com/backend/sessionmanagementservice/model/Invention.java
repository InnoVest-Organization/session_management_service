package com.backend.sessionmanagementservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "invention_data")
public class Invention {
    @Id
    private Long invention_id;
    private LocalTime bidStartTime;
    private LocalTime bidEndTime;
    private LocalDate bidStartDate;
    private Integer isLive;

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
}