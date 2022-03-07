package user.system.models;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity(name = "users")
public class User extends BaseEntity {

    @Column(name = "username", nullable = false)
    @Size(min = 4, max = 30, message = "Username must be between 4 and 30 characters")
    private String username;

    @Column(nullable = false)
    @Pattern(regexp = "^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[0-9])(?=.*?\\W).*$")
    @Size(min = 6, max = 50)
    private String password;

    @Column(nullable = false)
    @Email(regexp = "(?<username>[\\w]+[\\w._-])(@+)(?<provider>[A-Za-z]+\\.+[a-zA-Z]+)")
    private String email;

    @Column(name = "registered_on", nullable = false)
    private LocalDateTime registeredOn;

    @Column(name = "last_time_logged_in")
    private LocalDateTime lastTimeLoggedIn;

    @Column(name = "age", nullable = false)
    @Min(1)
    @Max(120)
    private Integer age;

    @Column(name = "is_deleted", nullable = false,
            columnDefinition = "BOOL default 0")
    private boolean isDeleted;

    @OneToOne
    private Town bornTown;

    @OneToOne
    private Town currentlyLiving;

    @Column(name = "first_name", nullable = false, length = 30)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 30)
    private String lastName;

    @ManyToMany
    private Set<User> friends;

    @OneToMany(mappedBy = "user")
    private Set<Albums> albums;

    public User() {
    }

    public User(String username, String password, String email, LocalDateTime registeredOn,
                Integer age, boolean isDeleted, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.registeredOn = registeredOn;
        this.age = age;
        this.isDeleted = isDeleted;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(LocalDateTime registeredOn) {
        this.registeredOn = registeredOn;
    }

    public LocalDateTime getLastTimeLoggedIn() {
        return lastTimeLoggedIn;
    }

    public void setLastTimeLoggedIn(LocalDateTime lastTimeLoggedIn) {
        this.lastTimeLoggedIn = lastTimeLoggedIn;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted() {
        isDeleted = true;
    }

    public Town getBornTown() {
        return bornTown;
    }

    public void setBornTown(Town bornTown) {
        this.bornTown = bornTown;
    }

    public Town getCurrentlyLiving() {
        return currentlyLiving;
    }

    public void setCurrentlyLiving(Town currentlyLiving) {
        this.currentlyLiving = currentlyLiving;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName(){
        return firstName + " " + lastName;
    }

    public Set<User> getFriends() {
        return friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }
}
