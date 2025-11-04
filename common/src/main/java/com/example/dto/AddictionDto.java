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

    public long getId(){
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCostPerDay(float costPerDay) {
        this.costPerDay = costPerDay;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public void setDetoxStartDate(Instant detoxStartDate) {
        this.detoxStartDate = detoxStartDate;
    }
}
