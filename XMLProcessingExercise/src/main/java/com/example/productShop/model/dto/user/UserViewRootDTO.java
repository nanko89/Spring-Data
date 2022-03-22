package com.example.productShop.model.dto.user;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserViewRootDTO {

    @XmlElement(name = "user")
    private List<UserWithProductsDTO> user;

    public UserViewRootDTO() {
    }

    public List<UserWithProductsDTO> getUser() {
        return user;
    }

    public void setUser(List<UserWithProductsDTO> user) {
        this.user = user;
    }
}
