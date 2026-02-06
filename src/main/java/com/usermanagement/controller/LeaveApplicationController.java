package com.usermanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usermanagement.entity.LeaveApplication;
import com.usermanagement.requestDto.LeaveRequestDto;
import com.usermanagement.responseDto.ResponseMessageDto;
import com.usermanagement.service.LeaveApplicationService;

@RestController
@RequestMapping("/api/employee/leave")
public class LeaveApplicationController {

	@Autowired
	private LeaveApplicationService service;

	@PostMapping("/apply")
	public ResponseEntity<?> applyLeave(@RequestBody LeaveRequestDto dto) {
		service.applyLeave(dto);
		return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "Leave Apply Sucessfully"));

	}
	
	public ResponseEntity<?> getAllApplyLeaveByEmployee()
	{
		service.getAllApplyLeaveByEmployee();
		return null;
	}
	
	
	
}
