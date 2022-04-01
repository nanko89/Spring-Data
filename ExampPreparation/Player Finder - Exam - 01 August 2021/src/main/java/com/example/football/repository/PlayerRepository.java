package com.example.football.repository;

import com.example.football.models.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    boolean existsByEmail(String email);

    @Query("SELECT p FROM Player p WHERE p.birthDate > '1995-01-01' AND p.birthDate < '2003-01-01' "+
            "ORDER BY  p.stat.shooting DESC, p.stat.passing DESC, p.stat.endurance DESC, p.lastName")
    List<Player> findAllOrderByShootingAndPassingAndEnduranceAndLastName();

    //@Query("SELECT p FROM Player p " +
    //            "WHERE p.birthDate BETWEEN :startDate AND :endDate " +
    //            "ORDER BY p.stat.shooting DESC, p.stat.passing DESC , p.stat.endurance DESC, p.lastName")
    //    List<Player> findAllByBirthDateBetweenOrderByShootingByPassingByEnduranceAndLastName
    //            (@Param("startDate") LocalDate startDate, @Param("endDate")LocalDate endDate);

//    List<Player> findAllByBirthDateBetweenOrderByStatShootingDescStatPassingDescStatEnduranceDescLastNameAsc(LocalDate start, LocalDate end);
}
