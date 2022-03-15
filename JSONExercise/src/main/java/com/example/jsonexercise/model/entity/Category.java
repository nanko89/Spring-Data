package com.example.jsonexercise.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "categories")
public class Category extends BaseEntity{

    @Column(length = 15, nullable = false, unique = true)
    private String name;

    public Category() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
