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


    public AddictionDetailsDto(long id, String name, BigDecimal costPerDay, Instant createdAt, int numberOfIncidents, List<IncidentDto> lastIncidents){
        this.id = id;
        this.name = name;
        this.costPerDay = costPerDay;
        this.createdAt = createdAt;
        this.numberOfIncidents = numberOfIncidents;
        this.lastIncidents = lastIncidents;
    }

    @Override
    public String toString() {
        return "Addiction Details:\n" +
                "Name: " + name + "\n" +
                "Cost per day: " + costPerDay + "\n" +
                "Created at: " + createdAt + "\n" +
                "Number of incidents: " + numberOfIncidents;
    }
}
