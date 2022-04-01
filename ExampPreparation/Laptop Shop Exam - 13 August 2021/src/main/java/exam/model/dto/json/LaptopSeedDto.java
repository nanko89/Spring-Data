package exam.model.dto.json;

import com.google.gson.annotations.Expose;
import exam.model.entity.enums.WarrantyType;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

public class LaptopSeedDto implements Serializable {

    @Expose
    @Size(min = 8)
    @NotBlank
    private String macAddress;
    @Expose
    @Positive
    @NotNull
    private Double cpuSpeed;
    @Expose
    @Min(8)
    @Max(128)
    @NotNull
    private Integer ram;
    @Expose
    @Min(128)
    @Max(1024)
    @NotNull
    private Integer storage;
    @Expose
    @Size(min = 10)
    @NotBlank
    private String description;
    @Expose
    @Positive
    @NotNull
    private BigDecimal price;
    @Expose
    @NotNull
    private WarrantyType warrantyType;
    @Expose
    @NotNull
    private ShopNameDto shop;

    public LaptopSeedDto() {
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public Double getCpuSpeed() {
        return cpuSpeed;
    }

    public void setCpuSpeed(Double cpuSpeed) {
        this.cpuSpeed = cpuSpeed;
    }

    public Integer getRam() {
        return ram;
    }

    public void setRam(Integer ram) {
        this.ram = ram;
    }

    public Integer getStorage() {
        return storage;
    }

    public void setStorage(Integer storage) {
        this.storage = storage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public WarrantyType getWarrantyType() {
        return warrantyType;
    }

    public void setWarrantyType(WarrantyType warrantyType) {
        this.warrantyType = warrantyType;
    }

    public ShopNameDto getShop() {
        return shop;
    }

    public void setShop(ShopNameDto shop) {
        this.shop = shop;
    }
}
