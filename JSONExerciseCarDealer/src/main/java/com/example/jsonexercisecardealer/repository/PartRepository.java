package com.example.jsonexercisecardealer.repository;

import com.example.jsonexercisecardealer.models.entity.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartRepository extends JpaRepository<Part,Long> {
}
