package com.example.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public class AddictionDetailsDto {
    private long id;
    private String name;
    private BigDecimal costPerDay;
    private Instant deadline;
    private Instant createdAt;
    private int numberOfIncidents;
    private List<IncidentDto> lastIncidents;
    private int limitOfLastIncidents;

    public AddictionDetailsDto(){}
}
