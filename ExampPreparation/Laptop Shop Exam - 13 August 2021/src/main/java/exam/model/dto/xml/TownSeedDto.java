package exam.model.dto.xml;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
@XmlAccessorType(XmlAccessType.FIELD)
public class TownSeedDto implements Serializable {
    @XmlElement(name = "name")
    @Size(min = 2)
    @NotBlank
    private String name;
    @XmlElement(name = "population")
    @Positive
    private Integer population;
    @XmlElement(name = "travel-guide")
    @Size(min = 10)
    @NotBlank
    private String travelGuide;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public String getTravelGuide() {
        return travelGuide;
    }

    public void setTravelGuide(String travelGuide) {
        this.travelGuide = travelGuide;
    }
}
