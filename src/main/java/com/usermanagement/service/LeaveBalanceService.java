package com.usermanagement.service;

import com.usermanagement.entity.Employee;

public interface LeaveBalanceService {
	public void createLeaveBalance(Employee employee, int year);
	public void processYearEndCarryForward(int oldYear, int newYear);
}
