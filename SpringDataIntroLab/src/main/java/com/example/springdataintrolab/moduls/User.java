package com.example.springdataintrolab.moduls;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name =  "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    private int age;

    @OneToMany(mappedBy = "user")
    private Set<Account> account;

    public User() {
        this.account = new HashSet<>();
    }

    public User(String username, int age) {
        this.username = username;
        this.age = age;
        this.account = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Set<Account> getAccount() {
        return Collections.unmodifiableSet(account);
    }

    public void setAccount(Set<Account> account) {
        this.account = account;
    }
}
