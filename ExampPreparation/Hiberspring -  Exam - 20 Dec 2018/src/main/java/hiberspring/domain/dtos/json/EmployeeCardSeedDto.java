package hiberspring.domain.dtos.json;

import javax.validation.constraints.NotNull;

public class EmployeeCardSeedDto {
    @NotNull
    private String number;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
