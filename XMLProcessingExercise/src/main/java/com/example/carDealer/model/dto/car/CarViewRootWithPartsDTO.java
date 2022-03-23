package com.example.carDealer.model.dto.car;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarViewRootWithPartsDTO {

@XmlElement(name = "cars")
private List<CarWithPartDTO> cars;

    public List<CarWithPartDTO> getCars() {
        return cars;
    }

    public void setCars(List<CarWithPartDTO> cars) {
        this.cars = cars;
    }
}
