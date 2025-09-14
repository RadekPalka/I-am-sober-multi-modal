package com.example.addictions.dto;

import java.math.BigDecimal;
import java.time.Instant;

public class AddictionDto {
    private int id;
    private String name;
    private float costPerDay;
    private String deadline;
    private Instant detoxStartDate;

    public String getName(){
        return name;
    }
    public float getCostPerDay(){
        return costPerDay;
    }
}
