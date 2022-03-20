package com.example.productStore.model.dto;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.Size;

public class UserSeedDTO {

    @Expose
    private String firstName;

    @Expose
    @Size(min = 3)
    private String lastName;

    @Expose
    private Integer age;

    public UserSeedDTO() {
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
