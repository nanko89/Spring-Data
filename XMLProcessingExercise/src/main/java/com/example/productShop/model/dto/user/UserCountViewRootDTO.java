package com.example.productShop.model.dto.user;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserCountViewRootDTO {
    @XmlAttribute
    private int count;
    @XmlElement(name = "user")
    private List<UserCountWithProductsDTO> users;

    public UserCountViewRootDTO() {
    }

    public List<UserCountWithProductsDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserCountWithProductsDTO> users) {
        this.users = users;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
