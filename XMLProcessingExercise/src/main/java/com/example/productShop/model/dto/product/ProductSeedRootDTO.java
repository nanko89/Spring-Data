package com.example.productShop.model.dto.product;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductSeedRootDTO {

    @XmlElement(name = "product")
    private List<ProductSeedDTO> products;

    public List<ProductSeedDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductSeedDTO> products) {
        this.products = products;
    }
}
