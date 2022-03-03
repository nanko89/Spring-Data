package Ex4HospitalDatabase;

import com.mysql.cj.jdbc.Blob;
import entity.BasicEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "patients")
@Inheritance(strategy = InheritanceType.JOINED)
public class Patient extends BasicEntity {

    @Column(name = "first_name", nullable = false, length = 30)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 30)
    private String lastName;

    @Column(name = "address", length = 100)
    private String address;

    @Column(name = "email", length = 30)
    private String email;

    @Column(name = "birthdate", nullable = false)
    private LocalDate birthdate;

    @Column(name = "picture", columnDefinition = "BLOB")
    private byte[] picture;

    @Column(name = "is_have_insurance", nullable = false)
    private boolean isHaveInsurance;

    @OneToMany(mappedBy = "patient")
    private Set<Diagnose> diagnoses;

    @OneToMany(mappedBy = "patient")
    private Set<Medicament> medicaments;

    @OneToMany(mappedBy = "patient")
    private Set<Visitation> visitations;

    public Patient() {
        this.medicaments = new HashSet<>();
        this.visitations = new HashSet<>();
        this.diagnoses = new HashSet<>();
    }

    public Patient(String firstName, String lastName, LocalDate birthdate, boolean isHaveInsurance) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.isHaveInsurance = isHaveInsurance;
        this.medicaments = new HashSet<>();
        this.visitations = new HashSet<>();
        this.diagnoses = new HashSet<>();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public boolean isHaveInsurance() {
        return isHaveInsurance;
    }

    public void setHaveInsurance(boolean haveInsurance) {
        isHaveInsurance = haveInsurance;
    }

    public Set<Diagnose> getDiagnoses() {
        return diagnoses;
    }

    public void setDiagnoses(Set<Diagnose> diagnoses) {
        this.diagnoses = diagnoses;
    }

    public Set<Medicament> getMedicaments() {
        return medicaments;
    }

    public void setMedicaments(Set<Medicament> medicaments) {
        this.medicaments = medicaments;
    }

    public Set<Visitation> getVisitations() {
        return visitations;
    }

    public void setVisitations(Set<Visitation> visitations) {
        this.visitations = visitations;
    }
}
