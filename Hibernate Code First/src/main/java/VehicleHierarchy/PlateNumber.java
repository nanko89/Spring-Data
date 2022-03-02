package VehicleHierarchy;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "plate_numbers")
public class PlateNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    private String number;

    public PlateNumber() {
    }

    public PlateNumber(String number) {
        this.number = number;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
