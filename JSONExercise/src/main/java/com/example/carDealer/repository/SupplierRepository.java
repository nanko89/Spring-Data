package com.example.carDealer.repository;

import com.example.carDealer.models.dto.SupplierLocalDTO;
import com.example.carDealer.models.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier,Long> {

    @Query("SELECT new com.example.carDealer.models.dto.SupplierLocalDTO (s.id, s.name, size(s.parts)) " +
            " FROM Supplier s" +
            " WHERE s.isImporter = false ")
    List<SupplierLocalDTO> findAllByImporterIsFalse();
}
