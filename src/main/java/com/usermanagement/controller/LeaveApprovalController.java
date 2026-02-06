package com.usermanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usermanagement.requestDto.ApproveLeaveRequestDto;
import com.usermanagement.service.LeaveApprovalService;

@RestController
@RequestMapping("/api/manager/leave")
public class LeaveApprovalController {

	@Autowired
	private LeaveApprovalService service;

	@PostMapping("/approve/{id}")
	public void approve(@RequestBody ApproveLeaveRequestDto requestDto) {
		service.approveLeave(requestDto);

	}
}
