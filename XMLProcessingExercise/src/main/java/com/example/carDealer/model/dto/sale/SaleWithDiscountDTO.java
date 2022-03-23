package com.example.carDealer.model.dto.sale;

import com.example.carDealer.model.dto.car.CarInfoDTO;
import com.example.carDealer.model.entity.Discount;

import javax.xml.bind.annotation.*;
import java.math.BigDecimal;

@XmlRootElement(name = "sale")
@XmlAccessorType(XmlAccessType.FIELD)
public class SaleWithDiscountDTO {

    @XmlElement(name = "car")
    private CarInfoDTO car;

    @XmlElement(name = "customer-name")
    private String customerName;

    @XmlElement(name = "discount")
    @XmlEnumValue("")
    private Discount discount;

    @XmlElement(name = "price")
    private BigDecimal price;

    @XmlElement(name = "price-with-discount")
    private BigDecimal priceWithDiscount;

    public CarInfoDTO getCar() {
        return car;
    }

    public void setCar(CarInfoDTO car) {
        this.car = car;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPriceWithDiscount() {
        return priceWithDiscount;
    }

    public void setPriceWithDiscount(BigDecimal priceWithDiscount) {
        this.priceWithDiscount = priceWithDiscount;
    }
}
