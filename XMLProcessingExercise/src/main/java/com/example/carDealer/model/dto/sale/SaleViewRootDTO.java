package com.example.carDealer.model.dto.sale;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "sales")
@XmlAccessorType(XmlAccessType.FIELD)
public class SaleViewRootDTO {

    private List<SaleWithDiscountDTO> sales;

    public List<SaleWithDiscountDTO> getSales() {
        return sales;
    }

    public void setSales(List<SaleWithDiscountDTO> sales) {
        this.sales = sales;
    }
}
