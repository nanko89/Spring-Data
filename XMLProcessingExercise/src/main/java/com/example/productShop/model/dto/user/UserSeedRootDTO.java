package com.example.productShop.model.dto.user;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserSeedRootDTO {

    @XmlElement(name = "user")
    private List<UserSeedDTO> user;

    public List<UserSeedDTO> getUser() {
        return user;
    }

    public void setUser(List<UserSeedDTO> user) {
        this.user = user;
    }
}
