package com.example.productStore.repository;

import com.example.productStore.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u from users u" +
            " WHERE (SELECT count(p) FROM products p WHERE p.seller.id = u.id) > 0 " +
            "ORDER BY u.lastName,u.firstName")
    List<User> findAllUsersWithMoreThenOneSoldProductOrderByLastNameThenByFirstName();

    @Query("SELECT u from users u" +
            " WHERE (SELECT count(p) FROM products p WHERE p.seller.id = u.id) > 0 ")
    List<User> findAllUsersWithMoreThenOneSoldProduct();

}
