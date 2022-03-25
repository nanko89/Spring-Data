package softuni.exam.models.dto.xml;

import softuni.exam.models.entity.enums.Rating;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "seller")
@XmlAccessorType(XmlAccessType.FIELD )
public class SellerSeedDto {

    @Size(min = 2, max = 19)
    @XmlElement(name = "first-name")
    private String firstName;

    @Size(min = 2, max = 19)
    @XmlElement(name = "last-name")
    private String lastName;

    @Email
    @XmlElement(name = "email")
    private String email;

    @NotNull
    @XmlElement(name = "rating")
    private Rating rating;

    @NotBlank
    @XmlElement(name = "town")
    private String town;

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

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }
}
