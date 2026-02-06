package com.usermanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.usermanagement.entity.LeaveBalance;

public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance, Long> {

	Optional<LeaveBalance> findByEmployeeIdAndLeaveTypeIdAndYear(Long employeeId, Long leaveTypeId, int year);

	List<LeaveBalance> findByEmployeeId(Long employeeId);
}
