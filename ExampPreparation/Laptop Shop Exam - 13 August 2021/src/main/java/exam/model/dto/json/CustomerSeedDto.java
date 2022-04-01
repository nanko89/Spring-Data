package exam.model.dto.json;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class CustomerSeedDto implements Serializable {

    @Expose
    @Size(min = 2)
    @NotBlank
    private String firstName;
    @Expose
    @Size(min = 2)
    @NotBlank
    private String lastName;
    @Expose
    @Email
    @NotNull
    private String email;
    @Expose
    @NotNull
    private String registeredOn;
    @Expose
    @NotNull
    private TownDto town;

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

    public String getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(String registeredOn) {
        this.registeredOn = registeredOn;
    }

    public TownDto getTown() {
        return town;
    }

    public void setTown(TownDto town) {
        this.town = town;
    }
}
