package com.usermanagement.service;

import java.util.List;
import com.usermanagement.entity.LeaveApplication;
import com.usermanagement.requestDto.LeaveRequestDto;

public interface LeaveApplicationService {
	
	public LeaveApplication applyLeave(LeaveRequestDto leaveRequestDto);

	public List<LeaveApplication> getAllApplyLeaveByEmployee(Long employeeId);

	public void cancelLeave(Long id);

}
