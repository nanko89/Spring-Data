package com.example.productShop.model.dto.category;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "categories")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategorySeedRootDTO {

    @XmlElement(name = "category")
    private List<CategorySeedDTO> categories;

    public List<CategorySeedDTO> getCategories() {
        return categories;
    }

    public void setCategories(List<CategorySeedDTO> categories) {
        this.categories = categories;
    }
}
