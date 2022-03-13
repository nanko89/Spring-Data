package auto.mapping.exercise.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

public class UserLoginDTO {

    @Email(message = "Invalid email address! Please enter valid email address")
    private String email;
    @Pattern(regexp = "[A-Za-z\\d]{6,}", message = "Enter valid password! Length must be at least 6 symbols and" +
            " must contain at least 1 uppercase, 1 lowercase letter and 1 digit")
    private String password;

    public UserLoginDTO() {
    }

    public UserLoginDTO(String email, String password) {
        this.email = email;
        this.password = password;
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
}
