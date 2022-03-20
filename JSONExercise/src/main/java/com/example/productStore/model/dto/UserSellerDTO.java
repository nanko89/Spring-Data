package com.example.productStore.model.dto;

import com.google.gson.annotations.Expose;

import java.util.Set;

public class UserSellerDTO {
    @Expose
    private String userFirstName;
    @Expose
    private String userLastName;
    @Expose
    private Integer age;
    @Expose
    private Set<ProductsSoldDTO> soldProducts;

    public UserSellerDTO() {
    }

    public UserSellerDTO(String userFirstName, String userLastName, Integer age, Set<ProductsSoldDTO> soldProducts) {
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.age = age;
        this.soldProducts = soldProducts;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Set<ProductsSoldDTO> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(Set<ProductsSoldDTO> soldProducts) {
        this.soldProducts = soldProducts;
    }
}
