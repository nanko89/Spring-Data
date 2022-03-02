package VehicleHierarchy;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue(value = "truck")
public class Truck extends Vehicle {

    private static final  String type = "TRUCK";

    private double loadCapacity;

    public Truck() {
    }

    public Truck(String type, String model, BigDecimal price, String fuelType, double loadCapacity) {
        super(type, model, price, fuelType);
        this.loadCapacity = loadCapacity;
    }

    public double getLoadCapacity() {
        return loadCapacity;
    }

    public void setLoadCapacity(double loadCapacity) {
        this.loadCapacity = loadCapacity;
    }
}
