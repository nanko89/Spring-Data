package com.example.carDealer.models.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "sales")
public class Sale extends BaseEntity{
    @Column
    @Enumerated(EnumType.STRING)
    private Discount discount;
    @Column(name = "car_id")
    private Long carId;
    @Column(name = "customer_id")
    private Long customerId;

    public Sale() {
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sale sale = (Sale) o;
        return discount == sale.discount && Objects.equals(carId, sale.carId) && Objects.equals(customerId, sale.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(discount, carId, customerId);
    }
}
