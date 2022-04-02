package softuni.exam.models.entity;

import softuni.exam.models.entity.enums.ApartmentType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "apartments")
public class Apartment extends BaseEntity{

    @Enumerated(EnumType.STRING)
    @NotNull
    private ApartmentType apartmentType;
    @Column(nullable = false)
    private Double area;
    @ManyToOne
    private Town town;

    public Apartment() {
    }

    public ApartmentType getApartmentType() {
        return apartmentType;
    }

    public void setApartmentType(ApartmentType apartmentType) {
        this.apartmentType = apartmentType;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
    }
}
