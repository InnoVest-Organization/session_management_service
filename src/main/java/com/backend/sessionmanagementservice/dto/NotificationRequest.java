package com.backend.sessionmanagementservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {
    private List<String> emails;
    private Long inventionId;
    private String productDescription;
    private LocalDate bidStartDate;
    private LocalTime bidStartTime;
    private LocalTime bidEndTime;
}