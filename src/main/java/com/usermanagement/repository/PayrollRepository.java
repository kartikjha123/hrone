package com.usermanagement.repository;

import com.usermanagement.entity.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;
import java.util.List;

public interface PayrollRepository extends JpaRepository<Payroll, Long> {
    @Query("SELECT p FROM Payroll p WHERE p.employee.id = :employeeId AND p.month = :month AND p.year = :year")
    Optional<Payroll> findByEmployeeIdAndMonthAndYear(@Param("employeeId") Long employeeId, @Param("month") Integer month, @Param("year") Integer year);
    List<Payroll> findByMonthAndYear(Integer month, Integer year);
}
