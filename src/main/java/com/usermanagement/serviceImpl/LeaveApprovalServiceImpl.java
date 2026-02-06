package com.usermanagement.serviceImpl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usermanagement.entity.LeaveApplication;
import com.usermanagement.entity.LeaveBalance;
import com.usermanagement.repository.LeaveApplicationRepository;
import com.usermanagement.repository.LeaveBalanceRepository;
import com.usermanagement.requestDto.ApproveLeaveRequestDto;
import com.usermanagement.service.LeaveApprovalService;

import jakarta.transaction.Transactional;

@Service
public class LeaveApprovalServiceImpl implements LeaveApprovalService {

	@Autowired
	private LeaveApplicationRepository leaveApplicationRepository;
	
	@Autowired
	private LeaveBalanceRepository leaveBalanceRepository;

	@Override
	@Transactional
	public void approveLeave(ApproveLeaveRequestDto requestDto) {
		// TODO Auto-generated method stub
		
		  LeaveApplication app = leaveApplicationRepository.findById(requestDto.getLeaveApplicationId()).orElseThrow();
	      LeaveBalance balance = leaveBalanceRepository.findByEmployeeIdAndLeaveTypeIdAndYear(app.getEmployee().getId(),app.getLeaveType().getId(),
	         app.getFromDate().getYear()).orElseThrow();

	        balance.setUsedLeaves(balance.getUsedLeaves() + app.getTotalDays());
	        balance.setRemainingLeaves(balance.getRemainingLeaves() - app.getTotalDays());

	        app.setStatus("APPROVED");
	        app.setManagerId(app.getManagerId());
	        app.setManagerComment(requestDto.getComment());
	        app.setActionDate(LocalDateTime.now());
	    }
}





