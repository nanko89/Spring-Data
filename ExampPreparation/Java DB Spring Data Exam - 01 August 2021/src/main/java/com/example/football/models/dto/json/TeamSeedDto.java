package com.example.football.models.dto.json;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TeamSeedDto {

    @Size(min = 3)
    @NotBlank
    private String name;
    @Size(min = 3)
    @NotBlank
    private String stadiumName;
    @Min(1000)
    @NotNull
    private Integer fanBase;
    @Size(min = 10)
    @NotBlank
    private String history;
    @NotNull
    private String townName;

    public TeamSeedDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStadiumName() {
        return stadiumName;
    }

    public void setStadiumName(String stadiumName) {
        this.stadiumName = stadiumName;
    }

    public Integer getFanBase() {
        return fanBase;
    }

    public void setFanBase(Integer fanBase) {
        this.fanBase = fanBase;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }
}
