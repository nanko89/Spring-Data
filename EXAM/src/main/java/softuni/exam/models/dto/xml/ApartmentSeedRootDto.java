package softuni.exam.models.dto.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "apartments")
@XmlAccessorType(XmlAccessType.FIELD)
public class ApartmentSeedRootDto {
    @XmlElement(name = "apartment")
    private List<ApartmentSeedDto> apartments;

    public List<ApartmentSeedDto> getApartments() {
        return apartments;
    }

    public void setApartments(List<ApartmentSeedDto> apartments) {
        this.apartments = apartments;
    }
}
