package user.system.models;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "towns")
public class Town extends BaseEntity {

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "country", nullable = false, length = 50)
    private String country;

    public Town() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
