package com.usermanagement.service;

import java.util.List;

import com.usermanagement.entity.LeaveType;

public interface LeavePolicyService {
	
	public LeaveType createLeaveType(LeaveType leaveType);
	
	 public List<LeaveType> getAllLeaveTypes();
	

}
