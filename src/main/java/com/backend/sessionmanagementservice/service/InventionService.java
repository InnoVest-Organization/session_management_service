package com.backend.sessionmanagementservice.service;

import com.backend.sessionmanagementservice.model.Invention;
import com.backend.sessionmanagementservice.repository.InventionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class InventionService {

    @Autowired
    private InventionRepository inventionRepository;

    @Scheduled(cron = "0 * * * * *") // Run at the start of every minute (0 seconds)
    public void updateLiveStatus() {
        List<Invention> inventions = inventionRepository.findAll();
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        for (Invention invention : inventions) {
            boolean isLive = isInventionLive(invention, currentDate, currentTime);
            invention.setIsLive(isLive ? 1 : 0);
        }

        inventionRepository.saveAll(inventions);
    }

    private boolean isInventionLive(Invention invention, LocalDate currentDate, LocalTime currentTime) {
        System.out.println(currentDate + " " + currentTime);
        // Check if current date matches bid start date
        if (!currentDate.equals(invention.getBidStartDate())) {
            return false;
        }

        // Check if current time is within bid time range
        System.out.println(invention.getBidStartTime() + " " + invention.getBidEndTime());
        return !currentTime.isBefore(invention.getBidStartTime()) &&
                !currentTime.isAfter(invention.getBidEndTime());
    }
}