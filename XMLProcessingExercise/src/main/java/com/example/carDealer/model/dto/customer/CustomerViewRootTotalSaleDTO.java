package com.example.carDealer.model.dto.customer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerViewRootTotalSaleDTO {
    @XmlElement(name = "customer")
    private List<CustomerTotalSaleDTO> customers;

    public List<CustomerTotalSaleDTO> getCustomers() {
        return customers;
    }

    public void setCustomers(List<CustomerTotalSaleDTO> customers) {
        this.customers = customers;
    }
}
