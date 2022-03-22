package com.example.productShop.model.dto.product;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductViewRootDTO {

    @XmlElement(name = "product")
    private List<ProductWithSellerDTO> products;

    public List<ProductWithSellerDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductWithSellerDTO> products) {
        this.products = products;
    }
}
