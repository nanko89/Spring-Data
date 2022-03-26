package softuni.exam.instagraphlite.models.dto.xml;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class PostSeedDto {

    @Size(min = 21)
    @NotBlank
    private String caption;
    @NotNull
    private UserNameDto user;
    @NotNull
    private PicturePathDto picture;

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public UserNameDto getUser() {
        return user;
    }

    public void setUser(UserNameDto user) {
        this.user = user;
    }

    public PicturePathDto getPicture() {
        return picture;
    }

    public void setPicture(PicturePathDto picture) {
        this.picture = picture;
    }
}
