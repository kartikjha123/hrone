package com.usermanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
		return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "Leave Applied Successfully"));
	}

	@GetMapping("/all/{employeeId}")
	public ResponseEntity<?> getAllApplyLeaveByEmployee(@PathVariable Long employeeId) {
		return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "Leave List Fetched Successfully",
				service.getAllApplyLeaveByEmployee(employeeId)));
	}

	@DeleteMapping("/cancel/{id}")
	public ResponseEntity<?> cancelLeave(@PathVariable Long id) {
		service.cancelLeave(id);
		return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "Leave Cancelled Successfully"));
	}
}
