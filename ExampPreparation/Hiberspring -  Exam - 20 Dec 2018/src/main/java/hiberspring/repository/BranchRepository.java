package hiberspring.repository;

import hiberspring.domain.entities.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {

    boolean existsByName(String name);

    Branch findByName(String name);
}
