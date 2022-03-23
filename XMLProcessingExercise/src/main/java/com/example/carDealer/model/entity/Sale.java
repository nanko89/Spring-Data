package com.example.carDealer.model.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "sales")
public class Sale extends BaseEntity{
    @Column
    @Enumerated(EnumType.STRING)
    private Discount discount;

    @ManyToOne
    private Car carId;

    @ManyToOne
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

    public void setCustomer(Customer customerId) {
        this.customerId = customerId;
    }
}
