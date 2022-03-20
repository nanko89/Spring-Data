package com.example.productStore.model.dto;

import com.google.gson.annotations.Expose;

import java.util.Set;

public class UsersCountDTO {
    @Expose
    private Integer usersCount;
    @Expose
    private Set<UserSellerDTO> users;

    public UsersCountDTO() {
    }

    public Integer getUserCount() {
        return usersCount;
    }

    public void setUserCount(Integer usersCount) {
        this.usersCount = usersCount;
    }

    public Set<UserSellerDTO> getUsers() {
        return users;
    }

    public void setUsers(Set<UserSellerDTO> users) {
        this.users = users;
    }
}
