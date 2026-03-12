package com.usermanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.usermanagement.entity.Employee;

public interface EmployeeRepository  extends JpaRepository<Employee, Long>{
	
	Optional<Employee> findByUser_Id(Long userId);
	
	// Manager ke under employees (pagination)
    Page<Employee> findByManagerId(Long managerId, Pageable pageable);

    // Unassigned employees
    List<Employee> findByManagerIsNull();
    
    long countByManagerId(Long managerId);

}
