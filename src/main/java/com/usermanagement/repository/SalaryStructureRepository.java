package com.usermanagement.repository;

import com.usermanagement.entity.SalaryStructure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface SalaryStructureRepository extends JpaRepository<SalaryStructure, Long> {
    @Query("SELECT ss FROM SalaryStructure ss WHERE ss.employee.id = :employeeId")
    Optional<SalaryStructure> findByEmployeeId(@Param("employeeId") Long employeeId);
}
