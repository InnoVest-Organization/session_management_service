package com.backend.sessionmanagementservice.event;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class StartBidEvent {
    private List<String> emails;
    private Long inventionId;
    private String productDescription;

    private LocalDate bidStartDate;
    private LocalTime bidStartTime;
    private LocalTime bidEndTime;
}
