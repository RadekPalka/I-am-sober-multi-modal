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

    public AddictionDetailsDto(){}

    @Override
    public String toString() {
        return "Addiction Details:\n" +
                "Name: " + name + "\n" +
                "Cost per day: " + costPerDay + "\n" +
                "Created at: " + createdAt + "\n" +
                "Number of incidents: " + numberOfIncidents;
    }

    public void setId(long id) {
        this.id = id;
    }
    public void setName(String name){
        this.name = name;
    }

    public void setCostPerDay(BigDecimal costPerDay) {
        this.costPerDay = costPerDay;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public void setNumberOfIncidents(int numberOfIncidents) {
        this.numberOfIncidents = numberOfIncidents;
    }

    public void setLastIncidents(List<IncidentDto> lastIncidents) {
        this.lastIncidents = lastIncidents;
    }
}
