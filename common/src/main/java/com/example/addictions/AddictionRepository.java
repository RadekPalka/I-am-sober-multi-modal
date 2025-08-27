package com.example.addictions;

import com.example.addictions.dto.AddictionDto;

import java.util.ArrayList;
import java.util.List;

public class AddictionRepository {
    private List<AddictionDto> addictions = new ArrayList<>();

    public List<AddictionDto> getAddictions(){
        return addictions;
    }

    public void setAddictions(List<AddictionDto> addictions){
        this.addictions.clear();
        this.addictions.addAll(addictions);
    }
}
