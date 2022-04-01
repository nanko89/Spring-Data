package exam.model.dto.xml;

import javax.validation.constraints.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.math.BigDecimal;

@XmlAccessorType(XmlAccessType.FIELD )
public class ShopSeedDto implements Serializable {

    @XmlElement(name = "address")
    @Size(min = 4)
    @NotBlank
    private String address;

    @XmlElement(name = "employee-count")
    @Min(1)
    @Max(50)
    @NotNull
    private Integer employeeCount;

    @XmlElement(name = "income")
    @Min(20000)
    @NotNull
    private BigDecimal income;

    @XmlElement(name = "name")
    @Size(min = 4)
    @NotBlank
    private String name;

    @XmlElement(name = "shop-area")
    @NotNull
    @Min(150)
    private Integer shopArea;

    @XmlElement(name = "town")
    @NotNull
    private TownNameDto town;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(Integer employeeCount) {
        this.employeeCount = employeeCount;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getShopArea() {
        return shopArea;
    }

    public void setShopArea(Integer shopArea) {
        this.shopArea = shopArea;
    }

    public TownNameDto getTown() {
        return town;
    }

    public void setTown(TownNameDto town) {
        this.town = town;
    }
}
