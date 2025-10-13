package com.example.dto;

import java.time.Instant;

public class AddictionDto {
    private long id;
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
