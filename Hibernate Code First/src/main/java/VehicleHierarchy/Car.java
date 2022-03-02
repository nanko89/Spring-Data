package VehicleHierarchy;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue(value = "car")
public class Car extends Vehicle {
    private static final  String type = "CARS";
    private int seats;

    public Car(String type, String model, BigDecimal price, String fuelType, int seats) {
        super(type, model, price, fuelType);
        this.seats = seats;
    }

    public Car() {
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }
}