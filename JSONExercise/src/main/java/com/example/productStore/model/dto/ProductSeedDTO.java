package com.example.productStore.model.dto;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class ProductSeedDTO {

    @Expose
    @Size(min = 3)
    private String name;

    @Expose
    private BigDecimal price;

    public ProductSeedDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
