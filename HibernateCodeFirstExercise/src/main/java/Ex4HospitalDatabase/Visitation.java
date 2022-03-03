package Ex4HospitalDatabase;

import entity.BasicEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "visitations")

public class Visitation extends BasicEntity {

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "comments", columnDefinition = "TEXT")
    private String comments;

    @ManyToOne(targetEntity = Patient.class)
    private Patient patient;

    public Visitation() {
    }

    public Visitation(LocalDate date, String comments) {
        this.date = date;
        this.comments = comments;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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
