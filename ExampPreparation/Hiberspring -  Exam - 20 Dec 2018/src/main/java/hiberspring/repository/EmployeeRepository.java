package hiberspring.repository;


import hiberspring.domain.dtos.EmployeeExportDto;
import hiberspring.domain.entities.Branch;
import hiberspring.domain.entities.Employee;
import hiberspring.domain.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT new hiberspring.domain.dtos.EmployeeExportDto(concat(e.firstName, ' ', e.lastName) , e.position, e.card.number )  " +
            "FROM Employee e WHERE e.branch.name LIKE ('%Branch%') AND size(e.branch.products) > 0 ORDER BY e.firstName, e.lastName, length(e.position) ")
    List<EmployeeExportDto> findByBranchContainsAndBranch_ProductsOrderByFirstNameLastName();
}
