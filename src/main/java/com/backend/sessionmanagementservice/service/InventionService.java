package com.backend.sessionmanagementservice.service;

import com.backend.sessionmanagementservice.dto.InvestorMatchRequest;
import com.backend.sessionmanagementservice.dto.NotificationRequest;
import com.backend.sessionmanagementservice.model.Invention;
import com.backend.sessionmanagementservice.repository.InventionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class InventionService {

    @Autowired
    private InventionRepository inventionRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Scheduled(cron = "0 * * * * *") // Run at the start of every minute (0 seconds)
    @Transactional
    public void updateLiveStatus() {
        List<Invention> inventions = inventionRepository.findAll();
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        for (Invention invention : inventions) {
            boolean isLive = isInventionLive(invention, currentDate, currentTime);
            Integer previousStatus = invention.getIsLive();
            invention.setIsLive(isLive ? 1 : 0);

            // If the invention just became live (status changed from 0 to 1)
            if (previousStatus == 0 && invention.getIsLive() == 1) {
                handleNewlyLiveInvention(invention);
            }
        }

        inventionRepository.saveAll(inventions);
    }

    @Transactional
    private void handleNewlyLiveInvention(Invention invention) {
        // Force loading of the aoi collection
        invention.getAoi().size();

        // Get matching investor emails
        List<String> investorEmails = getInvestorEmails(
                invention.getId(),
                invention.getAoi(),
                invention.getPaymentPackage());
        System.out.println(investorEmails);
        // Send notifications to matched investors
        if (investorEmails != null && !investorEmails.isEmpty()) {
            sendNotifications(
                    investorEmails,
                    invention.getId(),
                    invention.getProductDescription(),
                    invention.getBidStartDate(),
                    invention.getBidStartTime(),
                    invention.getBidEndTime());
        }
    }

    private List<String> getInvestorEmails(Long inventionId, List<String> aoi, String paymentPackage) {
        String investorServiceUrl = "http://localhost:5006/api/investors/match";

        InvestorMatchRequest request = new InvestorMatchRequest(inventionId, aoi, paymentPackage);

        ResponseEntity<List<String>> response = restTemplate.exchange(
                investorServiceUrl,
                HttpMethod.POST,
                new HttpEntity<>(request),
                new ParameterizedTypeReference<List<String>>() {
                });

        return response.getBody();
    }

    private String sendNotifications(List<String> emails, Long inventionId, String productDescription,
            LocalDate bidStartDate, LocalTime bidStartTime, LocalTime bidEndTime) {
        String notificationServiceUrl = "http://localhost:5005/api/notifications/startbid";

        NotificationRequest request = new NotificationRequest(emails, inventionId, productDescription, bidStartDate,
                bidStartTime, bidEndTime);
        ResponseEntity<String> res = restTemplate.postForEntity(notificationServiceUrl, request, String.class);
        return res.getBody();
    }

    private boolean isInventionLive(Invention invention, LocalDate currentDate, LocalTime currentTime) {
        // Check if current date matches bid start date
        if (!currentDate.equals(invention.getBidStartDate())) {
            return false;
        }

        // Check if current time is within bid time range
        return !currentTime.isBefore(invention.getBidStartTime()) &&
                !currentTime.isAfter(invention.getBidEndTime());
    }
}