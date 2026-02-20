package com.usermanagement.service;

import java.util.List;

import com.usermanagement.entity.LeaveType;

public interface LeavePolicyService {
	
	public LeaveType createLeaveType(LeaveType leaveType);
	
	 public List<LeaveType> getAllLeaveTypes();

	 public LeaveType updateLeaveType(Long id, LeaveType leaveType);

	 public void deleteLeaveType(Long id);
	

}
