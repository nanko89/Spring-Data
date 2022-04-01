package softuni.exam.models.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table()
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "serial_number", unique = true)
    private String serialNumber;

    @Column
    private BigDecimal price;

    @Column(name = "take_off")
    private LocalDateTime takeOff;
    @ManyToOne
    private Town toTown;
    @ManyToOne
    private Town fromTown;
    @ManyToOne
    private Passenger passenger;
    @ManyToOne
    private Plane plane;

    public Ticket() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getTakeOff() {
        return takeOff;
    }

    public void setTakeOff(LocalDateTime takeoff) {
        this.takeOff = takeoff;
    }

    public Town getToTown() {
        return toTown;
    }

    public void setToTown(Town toTown) {
        this.toTown = toTown;
    }

    public Town getFromTown() {
        return fromTown;
    }

    public void setFromTown(Town fromTown) {
        this.fromTown = fromTown;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public Plane getPlane() {
        return plane;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }
}
