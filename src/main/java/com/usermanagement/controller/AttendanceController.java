package com.usermanagement.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.usermanagement.entity.Attendance;
import com.usermanagement.entity.Employee;
import com.usermanagement.requestDto.AttendanceRequestDto;
import com.usermanagement.responseDto.ResponseMessageDto;
import com.usermanagement.service.AttendanceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Attendance Management", description = "APIs for Attendance tracking, OT calculation and Grid view")
@RestController
@RequestMapping("/attendance")
public class AttendanceController {

	@Autowired
	private AttendanceService attendanceService;

	@Operation(summary = "Mark Attendance Punch In", description = "Punch in or manual attendance entry for an employee.")
	@PostMapping("/attendance/punch-in")
	public ResponseEntity<?> punchIn(@RequestParam Long employeeId) {
		attendanceService.punchIn(employeeId);
		return ResponseEntity.ok("Punch In Successful");
	}

	@Operation(summary = "Mark Attendance Punch Out", description = "Punch Out or manual attendance entry for an employee.")
	@PostMapping("/attendance/punch-in")
	public ResponseEntity<?> punchOut(@RequestParam Long employeeId) {
		attendanceService.punchOut(employeeId);
		return ResponseEntity.ok("Punch In Successful");
	}

	@Operation(summary = "Update Attendance", description = "Updates an existing attendance record")
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateAttendance(@PathVariable Long id, @RequestBody AttendanceRequestDto request) {
		attendanceService.updateAttendance(id, request);
		return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "Attendance updated successfully"));
	}

	@Operation(summary = "Delete Attendance", description = "Deletes an attendance record")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteAttendance(@PathVariable Long id) {
		attendanceService.deleteAttendance(id);
		return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "Attendance deleted successfully"));
	}

	@GetMapping("/manager/attendance")
	public ResponseEntity<?> getManagerEmployeeAttendance(@RequestParam Long managerId) {

		return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "Attendance deleted successfully",
				attendanceService.getManagerEmployeeAttendance(managerId)));

	}

	@GetMapping("/attendance/approve/{id}")
	public ResponseEntity<?> approveAttendance(@PathVariable Long id, @RequestParam Long managerId) {

		attendanceService.approveAttendance(id, managerId);

		return ResponseEntity.ok("Attendance Approved");
	}
}
