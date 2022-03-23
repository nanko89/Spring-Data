package com.example.carDealer.model.dto.customer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerViewRootDTO {
    List<CustomerElementDTO> customers;

    public List<CustomerElementDTO> getCustomers() {
        return customers;
    }

    public void setCustomers(List<CustomerElementDTO> customers) {
        this.customers = customers;
    }
}
