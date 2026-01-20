package com.usermanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.usermanagement.entity.Employee;

public interface EmployeeRepository  extends JpaRepository<Employee, Long>{
	
	Optional<Employee> findByUserId(Long userId);

}
