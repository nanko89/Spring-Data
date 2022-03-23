package com.example.carDealer.reposiitory;

import com.example.carDealer.model.dto.supplier.SupplierLocalDTO;
import com.example.carDealer.model.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
@Query("SELECT new com.example.carDealer.model.dto.supplier.SupplierLocalDTO (s.id, s.name, size(s.parts) )" +
        " FROM Supplier s " +
        " WHERE s.isImporter = false")
    List<SupplierLocalDTO> findAllByImporterIsFalse();
}
