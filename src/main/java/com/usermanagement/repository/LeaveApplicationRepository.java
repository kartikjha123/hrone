package com.usermanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.usermanagement.entity.LeaveApplication;

@Repository
public interface LeaveApplicationRepository extends JpaRepository<LeaveApplication, Long> {

	List<LeaveApplication> findByEmployeeId(Long employeeId);

	List<LeaveApplication> findByManagerIdAndStatus(Long managerId, String status);
}
