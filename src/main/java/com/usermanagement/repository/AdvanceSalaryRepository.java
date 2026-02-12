package com.usermanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.usermanagement.entity.AdvanceSalary;

public interface AdvanceSalaryRepository extends JpaRepository<AdvanceSalary, Long> {

    @Query("SELECT a FROM AdvanceSalary a WHERE a.employee.id = :employeeId")
    List<AdvanceSalary> findByEmployeeId(@Param("employeeId") Long employeeId);

    List<AdvanceSalary> findByStatus(String status);
}
