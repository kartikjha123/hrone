package com.usermanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.usermanagement.entity.LeaveBalance;

public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance, Long> {

	@Query("SELECT lb FROM LeaveBalance lb WHERE lb.employee.id = :employeeId AND lb.leaveType.id = :leaveTypeId AND lb.year = :year")
	Optional<LeaveBalance> findByEmployeeIdAndLeaveTypeIdAndYear(@Param("employeeId") Long employeeId, @Param("leaveTypeId") Long leaveTypeId, @Param("year") int year);

	@Query("SELECT lb FROM LeaveBalance lb WHERE lb.employee.id = :employeeId")
	List<LeaveBalance> findByEmployeeId(@Param("employeeId") Long employeeId);
}
