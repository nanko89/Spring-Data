package VehicleHierarchy;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "vehicles")
    @Inheritance(strategy = InheritanceType.SINGLE_TABLE)
 @DiscriminatorColumn(name = "type")
    public abstract class Vehicle {

        @Id
        @GeneratedValue(strategy = GenerationType.TABLE)
        private long id;

        @Basic
        @Column(insertable = false, updatable = false)
        private String type;
        private String model;
        private BigDecimal price;
        private String fuelType;


    protected Vehicle(){}

    public Vehicle(String type, String model, BigDecimal price, String fuelType) {
        this.type = type;
        this.model = model;
        this.price = price;
        this.fuelType = fuelType;
    }
}

