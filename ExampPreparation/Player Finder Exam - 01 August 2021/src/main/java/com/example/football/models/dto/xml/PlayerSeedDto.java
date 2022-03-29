package com.example.football.models.dto.xml;

import com.example.football.models.entity.enums.Position;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class PlayerSeedDto {

    @Size(min = 2)
    @NotBlank
    @XmlElement(name = "first-name" )
    private String firstName;
    @Size(min = 2)
    @NotBlank
    @XmlElement(name = "last-name")
    private String lastName;
    @Email
    @NotNull
    @XmlElement
    private String email;
    @NotNull
    @XmlElement(name = "birth-date")
    private String birthDate;
    @NotNull
    @XmlElement
    private Position position;
    @NotNull
    @XmlElement(name = "town")
    private TownNameDto town;
    @NotNull
    @XmlElement(name = "team")
    private TeamNameDto team;
    @NotNull
    @XmlElement(name = "stat")
    private StatIdDto stat;

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

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public TownNameDto getTown() {
        return town;
    }

    public void setTown(TownNameDto town) {
        this.town = town;
    }

    public TeamNameDto getTeam() {
        return team;
    }

    public void setTeam(TeamNameDto team) {
        this.team = team;
    }

    public StatIdDto getStat() {
        return stat;
    }

    public void setStat(StatIdDto stat) {
        this.stat = stat;
    }
}