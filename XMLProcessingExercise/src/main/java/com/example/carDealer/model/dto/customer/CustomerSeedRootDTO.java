package com.example.carDealer.model.dto.customer;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerSeedRootDTO {
    @XmlElement(name = "customer")
    private List<CustomerSeedDTO> customers;

    public List<CustomerSeedDTO> getCustomers() {
        return customers;
    }

    public void setCustomers(List<CustomerSeedDTO> customers) {
        this.customers = customers;
    }
}
