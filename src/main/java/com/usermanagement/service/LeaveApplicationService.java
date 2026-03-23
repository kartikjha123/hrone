package com.usermanagement.service;

import java.time.LocalDate;
import java.util.List;

import com.usermanagement.entity.LeaveApplication;
import com.usermanagement.requestDto.LeaveRequestDto;
import com.usermanagement.responseDto.LeaveApplicationResponseDto;

public interface LeaveApplicationService {
	
	public LeaveApplication applyLeave(LeaveRequestDto leaveRequestDto);

	public List<LeaveApplicationResponseDto> getAllApplyLeaveByEmployee(Long employeeId);

	public void cancelLeave(Long id);
	
	 // ✅ New
    List<LeaveApplicationResponseDto> getLeavesByStatus(Long employeeId, String status);
    List<LeaveApplicationResponseDto> getLeavesByDateRange(Long employeeId, LocalDate from, LocalDate to);

}
