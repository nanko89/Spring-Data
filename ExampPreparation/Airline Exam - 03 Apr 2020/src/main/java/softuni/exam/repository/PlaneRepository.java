package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Plane;

@Repository
public interface PlaneRepository extends JpaRepository<Plane, Long> {

    boolean existsByRegisterNumber(String number);

    Plane findByRegisterNumber(String number);
}
