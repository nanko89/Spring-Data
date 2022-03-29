package softuni.exam.domain.dto.json;

import javax.validation.constraints.NotNull;

public class PictureDto {
    @NotNull
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String name) {
        this.url = name;
    }
}
