package Ex4HospitalDatabase;

import entity.BasicEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "medicaments")

public class Medicament extends BasicEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(targetEntity = Patient.class)
    private Patient patient;

    public Medicament() {
    }

    public Medicament(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
