package com.example.carDealer.model.dto.car;

import com.example.carDealer.model.dto.part.PartNameAndPriceDTO;

import javax.xml.bind.annotation.*;
import java.util.Set;

@XmlRootElement(name = "car")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarWithPartDTO {

@XmlAttribute(name = "make")
private String make;

@XmlAttribute(name = "model")
private String model;

@XmlAttribute(name = "travelled-distance")
private String travelledDistance;

@XmlElement(name = "part")
@XmlElementWrapper(name = "parts")
private Set<PartNameAndPriceDTO> parts;

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getTravelledDistance() {
        return travelledDistance;
    }

    public void setTravelledDistance(String travelledDistance) {
        this.travelledDistance = travelledDistance;
    }

    public Set<PartNameAndPriceDTO> getParts() {
        return parts;
    }

    public void setParts(Set<PartNameAndPriceDTO> parts) {
        this.parts = parts;
    }
}
