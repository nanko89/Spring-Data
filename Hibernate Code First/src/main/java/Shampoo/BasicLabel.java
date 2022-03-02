package Shampoo;

import javax.persistence.*;

@Entity
@Table(name = "labels")
public class BasicLabel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String color;

    @OneToOne (mappedBy = "label", targetEntity = BasicShampoo.class)
    private BasicShampoo basicShampoo;

    public BasicLabel() {
    }

    public BasicLabel(String color) {
        this.color = color;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public BasicShampoo getBasicShampoo() {
        return basicShampoo;
    }

    public void setBasicShampoo(BasicShampoo basicShampoo) {
        this.basicShampoo = basicShampoo;
    }
}
