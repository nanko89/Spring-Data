package com.example.carDealer.models.dto;

import com.google.gson.annotations.Expose;

import java.math.BigDecimal;

public class CustomersTotalSaleDTO {
    @Expose
    private String fullName;
    @Expose
    private Long boughtCars;
    @Expose
    private BigDecimal spendMoney;

    public CustomersTotalSaleDTO() {
    }

    public CustomersTotalSaleDTO(String fullName, Long boughtCars, BigDecimal spendMoney) {
        this.fullName = fullName;
        this.boughtCars = boughtCars;
        this.spendMoney = spendMoney;
    }



    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Long getBoughtCars() {
        return boughtCars;
    }

    public void setBoughtCars(Long boughtCars) {
        this.boughtCars = boughtCars;
    }

    public BigDecimal getSpendMoney() {
        return spendMoney;
    }

    public void setSpendMoney(BigDecimal spendMoney) {
        this.spendMoney = spendMoney;
    }
}
