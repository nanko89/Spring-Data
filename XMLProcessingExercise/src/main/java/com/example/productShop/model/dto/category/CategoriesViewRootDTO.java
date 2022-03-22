package com.example.productShop.model.dto.category;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "categories")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoriesViewRootDTO {

    @XmlElement(name = "category")
    private List<CategoryCountProductDTO> categories;

    public List<CategoryCountProductDTO> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryCountProductDTO> categories) {
        this.categories = categories;
    }
}
