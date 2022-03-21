package com.example.carDealer.models.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "sales")
public class Sale extends BaseEntity{
    @Column
    @Enumerated(EnumType.STRING)
    private Discount discount;

    @OneToOne
    private Car carId;

    @OneToOne
    private Customer customerId;

    public Sale() {
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public Car getCarId() {
        return carId;
    }

    public void setCarId(Car carId) {
        this.carId = carId;
    }

    public Customer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Customer customerId) {
        this.customerId = customerId;
    }
}
