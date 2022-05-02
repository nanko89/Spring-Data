package com.example.springdata.repository;

import com.example.springdata.entity.Company;
import com.example.springdata.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    boolean existsAllBy();

    Project findFirstByNameAndCompany(String name, String company);

}
