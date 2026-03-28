package com.usermanagement.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    
    
    
 // Today's attendance — paginated (existing methods ke neeche add karo)

 // Aaj ke sab records — pagination ke liye
 @Query("SELECT a FROM Attendance a WHERE a.date = :today")
 Page<Attendance> findTodayAttendance(@Param("today") LocalDate today, Pageable pageable);

 // Filter by punchStatus — sirf jinhe punch in hua
 @Query("SELECT a FROM Attendance a WHERE a.date = :today AND a.punchIn IS NOT NULL")
 Page<Attendance> findTodayPunchedIn(@Param("today") LocalDate today, Pageable pageable);

 // Filter by punchStatus — sirf jinhe punch out hua
 @Query("SELECT a FROM Attendance a WHERE a.date = :today AND a.punchOut IS NOT NULL")
 Page<Attendance> findTodayPunchedOut(@Param("today") LocalDate today, Pageable pageable);

 // Summary ke liye counts (no pagination needed)
 @Query("SELECT COUNT(a) FROM Attendance a WHERE a.date = :today AND a.punchIn IS NOT NULL")
 long countPunchedInToday(@Param("today") LocalDate today);

 @Query("SELECT COUNT(a) FROM Attendance a WHERE a.date = :today AND a.punchOut IS NOT NULL")
 long countPunchedOutToday(@Param("today") LocalDate today);

 @Query("SELECT COUNT(a) FROM Attendance a WHERE a.date = :today AND a.status = 'AA'")
 long countAbsentToday(@Param("today") LocalDate today);
 
 
 
 @Query("SELECT COUNT(a) FROM Attendance a WHERE " +
	       "MONTH(a.date) = :month AND YEAR(a.date) = :year " +
	       "AND a.employee.id IN :ids")
	long countByEmployeeIdsAndMonth(@Param("ids") List<Long> ids,
	        @Param("month") int month, @Param("year") int year);

	@Query("SELECT COUNT(a) FROM Attendance a WHERE " +
	       "MONTH(a.date) = :month AND YEAR(a.date) = :year")
	long countAllByMonth(@Param("month") int month, @Param("year") int year);
    
}
	
	
	
	

