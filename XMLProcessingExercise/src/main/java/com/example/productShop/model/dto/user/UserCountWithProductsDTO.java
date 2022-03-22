package com.example.productShop.model.dto.user;

import com.example.productShop.model.dto.product.ProductWithNameAndPriceDTO;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "uses")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserCountWithProductsDTO {

    @XmlAttribute(name = "first-name")
    private String firstName;
    @XmlAttribute(name = "last-name")
    private String lastName;
    @XmlElement(name = "product")
    @XmlElementWrapper(name = "sold-products")
    private List<ProductWithNameAndPriceDTO> products;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<ProductWithNameAndPriceDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductWithNameAndPriceDTO> products) {
        this.products = products;
    }
}
