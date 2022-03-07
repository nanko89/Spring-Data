package user.system.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import user.system.models.User;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findAllByEmailEndingWith(String provider);
    List<User> findAllByLastTimeLoggedInAfter(LocalDate localDate);
    Integer deleteAllByLastTimeLoggedInAfter(LocalDate localDate);
}
