package com.example.carDealer.model.dto.supplier;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "suppliers")
@XmlAccessorType(XmlAccessType.FIELD)
public class SupplierSeedRootDTO {

    @XmlElement(name = "supplier")
    private List<SupplierSeedDTO> suppliers;

    public List<SupplierSeedDTO> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<SupplierSeedDTO> suppliers) {
        this.suppliers = suppliers;
    }
}
