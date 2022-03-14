package auto.mapping.exercise.model.dto;

import java.math.BigDecimal;

public class GameAllDTO {

    private String title;
    private BigDecimal price;

    public GameAllDTO() {
    }

    public GameAllDTO(String title, BigDecimal price) {
        this.title = title;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("%s %.2f", title, price);
    }
}
