package hiberspring.domain.dtos;

public class EmployeeExportDto {

    private String name;
    private String position;
    private String cardNumber;

    public EmployeeExportDto() {
    }

    public EmployeeExportDto(String name, String position, String cardNumber) {
        this.name = name;
        this.position = position;
        this.cardNumber = cardNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}
