package com.example.carDealer.model.dto.part;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "parts")
@XmlAccessorType(XmlAccessType.FIELD)
public class PartSeedRootDTO {
    @XmlElement(name = "part")
    private List<PartSeedDTO> parts;

    public List<PartSeedDTO> getParts() {
        return parts;
    }

    public void setParts(List<PartSeedDTO> parts) {
        this.parts = parts;
    }
}
