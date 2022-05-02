package com.example.springdata.repository;

import com.example.springdata.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    Company findFirstByName(String name);

    boolean existsAllBy();
}
