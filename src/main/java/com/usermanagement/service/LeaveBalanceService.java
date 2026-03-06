package com.usermanagement.service;

import java.util.List;

import com.usermanagement.entity.Employee;
import com.usermanagement.responseDto.LeaveBalanceResponseDTO;

public interface LeaveBalanceService {
	public void createLeaveBalance(Employee employee, int year);
	public void processYearEndCarryForward(int oldYear, int newYear);
	List<LeaveBalanceResponseDTO> getLeaveBalance(Long employeeId, int year);
}
