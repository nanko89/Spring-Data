package user.system.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Email;
import java.util.Set;

@Entity(name = "pictures")
public class Picture extends BaseEntity{

    @Column(nullable = false)
    private String title;

    @Column(name = "caption")
    private String caption;

    @Column(name = "path", nullable = false)
    private String path;

    @ManyToMany(mappedBy = "pictures")
    private Set<Albums> albums;

    public Picture() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Set<Albums> getAlbums() {
        return albums;
    }

    public void setAlbums(Set<Albums> albums) {
        this.albums = albums;
    }
}
