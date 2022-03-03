package Ex5BillsPaymentSystem;

import entity.BasicEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User extends BasicEntity {

    @Column(name = "first_name", nullable = false, length = 30)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 30)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "billing_details", columnDefinition = "TEXT")
    private String billingDetails;

    public User() {
    }

    public User(String firstName, String lastName, String email, String password, String billingDetails) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.billingDetails = billingDetails;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBillingDetails() {
        return billingDetails;
    }

    public void setBillingDetails(String billingDetails) {
        this.billingDetails = billingDetails;
    }
}
