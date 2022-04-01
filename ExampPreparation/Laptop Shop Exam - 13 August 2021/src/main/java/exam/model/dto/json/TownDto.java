package exam.model.dto.json;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class TownDto implements Serializable {
    @Expose
    private String name;

    public TownDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
