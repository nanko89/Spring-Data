package exam.model.dto.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "shops")
@XmlAccessorType(XmlAccessType.FIELD )
public class ShopSeedRootDto implements Serializable {
    @XmlElement(name = "shop")
    private List<ShopSeedDto> shops;

    public List<ShopSeedDto> getShops() {
        return shops;
    }

    public void setShops(List<ShopSeedDto> shops) {
        this.shops = shops;
    }
}
