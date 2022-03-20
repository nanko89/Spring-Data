package com.example.jsonexercisecardealer.repository;

import com.example.jsonexercisecardealer.models.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car,Long> {
}
