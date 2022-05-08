package com.example.mobilele.entity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "brands")
public class Brand extends BaseEntity {

    private String name;

    @OneToMany(mappedBy = "brand")
    private Set<Model> models;

    public Brand() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
