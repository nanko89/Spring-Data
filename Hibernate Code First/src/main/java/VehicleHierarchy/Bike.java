package VehicleHierarchy;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue(value = "bike")
public class Bike extends Vehicle {
    private static final String type = "BIKE";

    private int gearsCount;

    public Bike() {
    }

    public Bike(String type, String model, BigDecimal price, String fuelType, int gearsCount) {
        super(type, model, price, fuelType);
        this.gearsCount = gearsCount;
    }

    public int getGearsCount() {
        return gearsCount;
    }

    public void setGearsCount(int gearsCount) {
        this.gearsCount = gearsCount;
    }
}