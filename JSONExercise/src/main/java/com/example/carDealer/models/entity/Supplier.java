package com.example.carDealer.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "suppliers")
public class Supplier extends BaseEntity {

   @Column
    private String name;
   @Column(name = "is_importer")
   private boolean isImporter;

   @OneToMany(mappedBy = "supplier")
   private Set<Part> parts;

    public Supplier() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isImporter() {
        return isImporter;
    }

    public void setImporter(boolean importer) {
        isImporter = importer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Supplier supplier = (Supplier) o;
        return isImporter == supplier.isImporter && Objects.equals(name, supplier.name) && Objects.equals(parts, supplier.parts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, isImporter, parts);
    }
}
