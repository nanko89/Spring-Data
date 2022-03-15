package com.example.jsonexercise.model.dto;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.Size;

public class CategoriesSeedDTO {

    @Expose
    @Size(min = 3, max = 15)
    private String name;

    public CategoriesSeedDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
