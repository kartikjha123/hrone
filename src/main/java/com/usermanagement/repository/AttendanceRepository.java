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
	
	
	
    // ✅ Sirf overtime wale records (overtimeHours > 0)
    @Query("SELECT a FROM Attendance a WHERE a.employee.id = :employeeId AND a.overtimeHours > 0")
    List<Attendance> findOvertimeByEmployee(@Param("employeeId") Long employeeId);

    // ✅ Manager ke under sab employees ka overtime
    @Query("SELECT a FROM Attendance a WHERE a.employee.manager.id = :managerId AND a.overtimeHours > 0")
    List<Attendance> findOvertimeByManager(@Param("managerId") Long managerId);

    // ✅ Month-wise overtime
    @Query("""
        SELECT a FROM Attendance a
        WHERE a.employee.id = :employeeId
        AND a.overtimeHours > 0
        AND MONTH(a.date) = :month
        AND YEAR(a.date) = :year
        """)
    List<Attendance> findOvertimeByEmployeeAndMonth(
        @Param("employeeId") Long employeeId,
        @Param("month") int month,
        @Param("year") int year);
}
	
	
	
	

