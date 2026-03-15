package com.usermanagement.service;

import com.usermanagement.responseDto.DashboardResponseDto;

public interface DashboardService {
	
	DashboardResponseDto getDashboard(Long empId);

}
