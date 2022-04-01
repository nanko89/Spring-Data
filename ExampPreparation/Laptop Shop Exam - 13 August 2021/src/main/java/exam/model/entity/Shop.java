package exam.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "shops")
public class Shop extends BaseEntity{

    @Column(unique = true, nullable = false)
    private String name;
    @Column(nullable = false)
    private BigDecimal income;
    @Column(nullable = false)
    private String address;
    @Column(name = "employee_count", nullable = false)
    private Integer employeeCount;
    @Column(name = "shop_area", nullable = false)
    private Integer shopArea;
    @ManyToOne(optional = false)
    private Town town;

    public Shop() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

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

    public Integer getShopArea() {
        return shopArea;
    }

    public void setShopArea(Integer shopArea) {
        this.shopArea = shopArea;
    }

    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
    }
}
