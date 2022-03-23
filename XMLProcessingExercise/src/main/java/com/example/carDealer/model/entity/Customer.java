package com.example.carDealer.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "customers")
public class Customer extends BaseEntity{
   @Column
    private LocalDateTime name;
   @Column(name = "birth_date")
    private String birthDate;
   @Column(name = "is_young_driver")
    private boolean isYoungDriver;

    public Customer() {
    }

    public LocalDateTime getName() {
        return name;
    }

    public void setName(LocalDateTime name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String  birthDate) {
        this.birthDate = birthDate;
    }

    public boolean isYoungDriver() {
        return isYoungDriver;
    }

    public void setYoungDriver(boolean youngDriver) {
        isYoungDriver = youngDriver;
    }

}
