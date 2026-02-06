package com.usermanagement.service;

import java.time.LocalDate;

import com.usermanagement.entity.Employee;
import com.usermanagement.entity.LeaveApplication;
import com.usermanagement.entity.LeaveType;
import com.usermanagement.requestDto.LeaveRequestDto;

public interface LeaveApplicationService {
	
	public LeaveApplication applyLeave(LeaveRequestDto leaveRequestDto);

	public LeaveApplication getAllApplyLeaveByEmployee();
	
	

}
