package Ex4HospitalDatabase;

import entity.BasicEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "diagnoses")

public class Diagnose extends BasicEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "comments", columnDefinition = "TEXT")
    private String comments;

    @ManyToOne()
    private Patient patient;

    public Diagnose() {
    }

    public Diagnose(String name, String comments) {
        this.name = name;
        this.comments = comments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

}
