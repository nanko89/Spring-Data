package Shampoo;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ingredients")

public class BasicIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double quantity;

    public BasicIngredient() {
    }

    public BasicIngredient(double quantity) {
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}
