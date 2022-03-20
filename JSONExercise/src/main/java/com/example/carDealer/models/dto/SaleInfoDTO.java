package com.example.carDealer.models.dto;

import com.example.carDealer.models.entity.Discount;
import com.google.gson.annotations.Expose;

public class SaleInfoDTO {
    @Expose
    private Long carId;
    @Expose
    private Discount discount;

    public SaleInfoDTO() {
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }
}
