package com.usermanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.usermanagement.entity.LeaveApplication;

@Repository
public interface LeaveApplicationRepository extends JpaRepository<LeaveApplication, Long> {

	@Query("SELECT la FROM LeaveApplication la WHERE la.employee.id = :employeeId")
	List<LeaveApplication> findByEmployeeId(@Param("employeeId") Long employeeId);

	List<LeaveApplication> findByManagerIdAndStatus(Long managerId, String status);
}
