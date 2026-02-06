package com.usermanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usermanagement.entity.LeaveType;
import com.usermanagement.responseDto.ResponseMessageDto;
import com.usermanagement.service.LeavePolicyService;

@RestController
@RequestMapping("/api/hr/leave-type")
public class LeavePolicyController {

	@Autowired
	private LeavePolicyService service;

	@PostMapping
	public ResponseEntity<?> create(@RequestBody LeaveType leaveType) {
		service.createLeaveType(leaveType);
		return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "Leave Created Sucessfully"));
	}

	@GetMapping
	public ResponseEntity<ResponseMessageDto> getAll() {

		List<LeaveType> list = service.getAllLeaveTypes();

		String message = list.isEmpty() ? "No Data Found" : "LeaveType list extracted successfully";

		return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), message, list));

	}

}
