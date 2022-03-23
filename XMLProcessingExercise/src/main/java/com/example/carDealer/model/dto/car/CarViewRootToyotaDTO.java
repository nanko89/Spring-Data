package com.example.carDealer.model.dto.car;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarViewRootToyotaDTO {

    @XmlElement(name = "car")
    private List<CarToyotaDTO> cars;

    public List<CarToyotaDTO> getCars() {
        return cars;
    }

    public void setCars(List<CarToyotaDTO> cars) {
        this.cars = cars;
    }
}
