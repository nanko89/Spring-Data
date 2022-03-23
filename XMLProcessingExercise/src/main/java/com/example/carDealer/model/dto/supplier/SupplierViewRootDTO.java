package com.example.carDealer.model.dto.supplier;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "suppliers")
@XmlAccessorType(XmlAccessType.FIELD)
public class SupplierViewRootDTO {
    @XmlElement(name = "supplier")
    private List<SupplierLocalDTO> suppliers;

    public List<SupplierLocalDTO> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<SupplierLocalDTO> suppliers) {
        this.suppliers = suppliers;
    }
}
