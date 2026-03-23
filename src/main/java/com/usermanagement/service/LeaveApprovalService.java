package com.usermanagement.service;

import java.util.List;

import com.usermanagement.requestDto.ApproveLeaveRequestDto;
import com.usermanagement.responseDto.LeaveApplicationResponseDto;

public interface LeaveApprovalService {
	
	//public void approveLeave(ApproveLeaveRequestDto request);
	
	
	void approveLeave(ApproveLeaveRequestDto requestDto);                          // approve/reject
    List<LeaveApplicationResponseDto> getPendingLeaves(Long managerId);            // pending list
    List<LeaveApplicationResponseDto> getAllLeavesForManager(Long managerId);      // all leaves

}
