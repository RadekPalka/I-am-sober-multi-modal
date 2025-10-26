package com.example.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public class AddictionDetailsDto {
    private long id;
    private String name;
    private BigDecimal costPerDay;
    private Instant createdAt;
    private int numberOfIncidents;
    private List<IncidentDto> lastIncidents;


    public void AddictionDto(long id, String name, BigDecimal costPerDay, Instant createdAt, int numberOfIncidents, List<IncidentDto> lastIncidents){
        this.id = id;
        this.name = name;
        this.costPerDay = costPerDay;
        this.createdAt = createdAt;
        this.numberOfIncidents = numberOfIncidents;
        this.lastIncidents = lastIncidents;
    }
}
