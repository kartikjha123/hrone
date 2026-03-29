package com.usermanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.usermanagement.entity.Employee;

public interface EmployeeRepository  extends JpaRepository<Employee, Long>{
	
	Optional<Employee> findByUser_Id(Long userId);
	
	// Manager ke under employees (pagination)
    Page<Employee> findByManagerId(Long managerId, Pageable pageable);

    // Unassigned employees
    List<Employee> findByManagerIsNull();
    
    long countByManagerId(Long managerId);

	List<Employee> findByManagerId(Long managerId);
	
	// Aaj birthday wale employees
	@Query("SELECT e FROM Employee e WHERE " +
	       "MONTH(e.dateOfBirth) = :month AND DAY(e.dateOfBirth) = :day")
	List<Employee> findTodayBirthdays(
	        @Param("month") int month,
	        @Param("day") int day);

	// Is month ke baaki birthdays (upcoming)
	@Query("SELECT e FROM Employee e WHERE " +
	       "MONTH(e.dateOfBirth) = :month AND DAY(e.dateOfBirth) > :day " +
	       "ORDER BY DAY(e.dateOfBirth) ASC")
	List<Employee> findUpcomingBirthdaysThisMonth(
	        @Param("month") int month,
	        @Param("day") int day);
	
	
	// Yeh method add karo
	Optional<Employee> findByEmployeeCode(String employeeCode);


}
