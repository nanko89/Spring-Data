package Shampoo;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "shampoo")
public class BasicShampoo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @OneToOne(optional = false)
    @JoinColumn(name = "label_id", referencedColumnName = "id")
    private BasicLabel label;

    @ManyToOne(optional = false)
    @JoinColumn(name = "batch_id", referencedColumnName = "id")
    private ProductionBatch batch;


    @ManyToMany
    @JoinTable(name = "shampoo_ingregients",
            joinColumns = @JoinColumn(name = "shampoo_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "ingregint", referencedColumnName = "id"))
    private Set<BasicIngredient> ingredients;

    public BasicShampoo() {
    }

    public BasicShampoo(String name, BasicLabel label , ProductionBatch batch) {
        this.name = name;
        this.label = label;
        this.batch = batch;
        this.ingredients = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BasicLabel getBasicLabel() {
        return label;
    }

    public void setBasicLabel(BasicLabel label) {
        this.label = label;
    }

    public BasicLabel getLabel() {
        return label;
    }

    public void setLabel(BasicLabel label) {
        this.label = label;
    }

    public ProductionBatch getBatch() {
        return batch;
    }

    public void setBatch(ProductionBatch batch) {
        this.batch = batch;
    }

    public Set<BasicIngredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<BasicIngredient> ingredients) {
        this.ingredients = ingredients;
    }
}
