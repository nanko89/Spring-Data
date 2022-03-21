package com.example.carDealer.models.dto;

import com.google.gson.annotations.Expose;

import java.util.HashSet;
import java.util.Set;

public class CarInfoDTO {
    @Expose
    private String make;
    @Expose
    private String model;
    @Expose
    private Long travelledDistance;
    @Expose
    private Set<PartNameAndPriseDTO> parts;

    public CarInfoDTO() {
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Long getTravelledDistance() {
        return travelledDistance;
    }

    public void setTravelledDistance(Long travelledDistance) {
        this.travelledDistance = travelledDistance;
    }

    public Set<PartNameAndPriseDTO> getParts() {
        return parts;
    }

    public void setParts(Set<PartNameAndPriseDTO> parts) {
        this.parts = parts;
    }
}
