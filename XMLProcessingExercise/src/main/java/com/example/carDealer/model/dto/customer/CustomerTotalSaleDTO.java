package com.example.carDealer.model.dto.customer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.math.BigInteger;

@XmlRootElement(name = "customer")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerTotalSaleDTO {

    @XmlAttribute(name = "full-name")
    private String name;
    @XmlAttribute(name = "bought-cars")
    private BigInteger boughtCars;
    @XmlAttribute(name = "spent-money")
    private BigDecimal spendMoney;

    public CustomerTotalSaleDTO() {
    }

    public CustomerTotalSaleDTO(String name, BigInteger boughtCars, BigDecimal spendMoney) {
        this.name = name;
        this.boughtCars = boughtCars;
        this.spendMoney = spendMoney;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigInteger getBoughtCars() {
        return boughtCars;
    }

    public void setBoughtCars(BigInteger boughtCars) {
        this.boughtCars = boughtCars;
    }

    public BigDecimal getSpendMoney() {
        return spendMoney;
    }

    public void setSpendMoney(BigDecimal spendMoney) {
        this.spendMoney = spendMoney;
    }
}
