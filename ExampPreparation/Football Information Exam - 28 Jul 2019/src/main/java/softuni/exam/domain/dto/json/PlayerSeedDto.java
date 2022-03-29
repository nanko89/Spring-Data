package softuni.exam.domain.dto.json;

import softuni.exam.domain.entities.Picture;
import softuni.exam.domain.entities.enums.Position;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class PlayerSeedDto {

    @NotNull
    private String firstName;
    @NotNull
    @Size(min = 3, max = 15)
    private String lastName;
    @Min(1)
    @Max(99)
    private Integer number;
    @Min(0)
    private BigDecimal salary;
    @NotNull
    private Position position;
    @NotNull
    private PictureDto picture;
    @NotNull
    private TeamDto team;

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

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public PictureDto getPicture() {
        return picture;
    }

    public void setPicture(PictureDto picture) {
        this.picture = picture;
    }

    public TeamDto getTeam() {
        return team;
    }

    public void setTeam(TeamDto team) {
        this.team = team;
    }
}
