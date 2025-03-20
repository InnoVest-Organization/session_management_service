package com.backend.sessionmanagementservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvestorMatchRequest {
    private Long inventionId;
    private List<String> aoi;
    private String paymentPackage;
}