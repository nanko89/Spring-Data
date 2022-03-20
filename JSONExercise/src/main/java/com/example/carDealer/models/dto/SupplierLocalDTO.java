package com.example.carDealer.models.dto;

import com.google.gson.annotations.Expose;

public class SupplierLocalDTO {
    @Expose
    private Long id;
    @Expose
    private String name;
    @Expose
    private int partsCount;


    public SupplierLocalDTO() {
    }

    public SupplierLocalDTO(Long id, String name, int partsCount) {
        this.id = id;
        this.name = name;
        this.partsCount = partsCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPartsCount() {
        return partsCount;
    }

    public void setPartsCount(int partsCount) {
        this.partsCount = partsCount;
    }
}
