package hiberspring.domain.dtos.json;

import javax.validation.constraints.NotNull;

public class BranchSeedDto {
    @NotNull
    private String name;
    @NotNull
    private String town;

    public BranchSeedDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }
}
