package softuni.exam.instagraphlite.models.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "pictures")
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String path;

    @Column(nullable = false)
    private Double size;

    @OneToMany(mappedBy = "picture", fetch = FetchType.EAGER)
    private Set<Post> posts;

    @OneToMany(mappedBy = "profilePicture", fetch = FetchType.EAGER)
    private Set<User> users;

    public Picture() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }
}
