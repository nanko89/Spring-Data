package hiberspring.domain.dtos.json;

import javax.validation.constraints.NotNull;

public class TownSeedDto {

    @NotNull
    private String name;
    @NotNull
    private Integer population;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }
}
