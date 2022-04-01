package exam.model.dto.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "towns")
@XmlAccessorType(XmlAccessType.FIELD)
public class TownSeedRootDto implements Serializable {
    @XmlElement(name = "town")
    private List<TownSeedDto> towns;

    public List<TownSeedDto> getTowns() {
        return towns;
    }

    public void setTowns(List<TownSeedDto> towns) {
        this.towns = towns;
    }
}
