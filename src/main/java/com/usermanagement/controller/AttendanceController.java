package com.usermanagement.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.usermanagement.requestDto.AttendanceRequestDto;
import com.usermanagement.responseDto.ResponseMessageDto;
import com.usermanagement.service.AttendanceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Attendance Management", description = "APIs for Attendance tracking, OT calculation and Grid view")
@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    // ✅ Constructor Injection
    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @Operation(summary = "Mark Attendance Punch In", description = "Punch in for an employee.")
    @PostMapping("/punch-in")
    public ResponseEntity<?> punchIn(@RequestParam Long employeeId) {
        attendanceService.punchIn(employeeId);
        return ResponseEntity.ok("Punch In Successful");
    }

    @Operation(summary = "Mark Attendance Punch Out", description = "Punch out for an employee.")
    @PostMapping("/punch-out")
    public ResponseEntity<?> punchOut(@RequestParam Long employeeId) {
        attendanceService.punchOut(employeeId);
        return ResponseEntity.ok("Punch Out Successful"); // ✅ Fixed message
    }

    @Operation(summary = "Update Attendance", description = "Updates an existing attendance record.")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateAttendance(@PathVariable Long id,
                                               @RequestBody AttendanceRequestDto request) {
        attendanceService.updateAttendance(id, request);
        return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "Attendance updated successfully"));
    }

    @Operation(summary = "Delete Attendance", description = "Deletes an attendance record.")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAttendance(@PathVariable Long id) {
        attendanceService.deleteAttendance(id);
        return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "Attendance deleted successfully"));
    }

    @Operation(summary = "Manager View Employee Attendance", description = "Fetch attendance of employees under a manager.")
    @GetMapping("/manager/attendance")
    public ResponseEntity<?> getManagerEmployeeAttendance(@RequestParam Long managerId) {
        return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(),
                "Attendance fetched successfully",
                attendanceService.getManagerEmployeeAttendance(managerId)));
    }

    @Operation(summary = "Approve Attendance", description = "Manager approves attendance of an employee.")
    @GetMapping("/approve/{id}") // ✅ Fixed duplicate /attendance/attendance URL
    public ResponseEntity<?> approveAttendance(@PathVariable Long id,
                                                @RequestParam Long managerId) {
        attendanceService.approveAttendance(id, managerId);
        return ResponseEntity.ok("Attendance Approved");
    }

    @Operation(summary = "Get Today's Attendance Status", description = "Returns today's punch-in/out status of an employee.")
    @GetMapping("/status/{employeeId}")
    public ResponseEntity<?> getTodayAttendanceStatus(@PathVariable Long employeeId) {
        return ResponseEntity.ok(attendanceService.getTodayAttendanceStatus(employeeId));
    }
    
    @Operation(
    	    summary = "Get My Attendance",
    	    description = "Login ke baad employee apni current month ki attendance dekh sakta hai."
    	)
    	@GetMapping("/my-attendance/{employeeId}")
    	public ResponseEntity<?> getMyAttendance(
    	        @PathVariable Long employeeId,
    	        @RequestParam(required = false) Integer month,
    	        @RequestParam(required = false) Integer year) {

    	    return ResponseEntity.ok(
    	        new ResponseMessageDto(
    	            HttpStatus.OK.value(),
    	            "Attendance fetched successfully",
    	            attendanceService.getMyAttendance(employeeId, month, year)
    	        )
    	    );
    	}
}