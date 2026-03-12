package com.usermanagement.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.usermanagement.entity.Attendance;
import com.usermanagement.entity.Employee;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    @Query("SELECT a FROM Attendance a WHERE a.employee.id = :employeeId AND a.date BETWEEN :startDate AND :endDate")
    List<Attendance> findByEmployeeIdAndDateBetween(@Param("employeeId") Long employeeId,
                                                    @Param("startDate") LocalDate startDate,
                                                    @Param("endDate") LocalDate endDate);

	Attendance findByEmployeeIdAndDate(Long employeeId, LocalDate now);

	List<Attendance> findByEmployeeIn(List<Employee> employees);
}
