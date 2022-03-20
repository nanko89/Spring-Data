package com.example.carDealer.models.dto;

import com.google.gson.annotations.Expose;

import java.util.HashSet;
import java.util.Set;

public class CustomersOrderedDTO {
    @Expose
    private Long id;

    @Expose
    private String name;

    private String firstName;

    private String lastName;

    @Expose
    private String birthdate;

    @Expose
    private boolean isYoungDriver;

    @Expose
    private Set<SaleInfoDTO> sales;

    public CustomersOrderedDTO() {
    }

    public CustomersOrderedDTO(Long id, String firstName, String lastName, String birthdate, boolean isYoungDriver) {
        this.id = id;
        this.name = firstName + " " + lastName;
        this.birthdate = birthdate;
        this.isYoungDriver = isYoungDriver;
        this.sales = new HashSet<>();
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public boolean isYoungDriver() {
        return isYoungDriver;
    }

    public void setYoungDriver(boolean youngDriver) {
        isYoungDriver = youngDriver;
    }

    public Set<SaleInfoDTO> getSales() {
        return sales;
    }

    public void setSales(Set<SaleInfoDTO> sales) {
        this.sales = sales;
    }
}
