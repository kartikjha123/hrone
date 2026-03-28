package com.usermanagement.controller;

import java.util.List;

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

import com.usermanagement.requestDto.AttendanceRequestDto;
import com.usermanagement.requestDto.BulkApproveRequestDto;
import com.usermanagement.responseDto.OvertimeResponseDto;
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
    
    @Operation(summary = "My Overtime", description = "Employee apna overtime dekhe month-wise")
    @GetMapping("/my-overtime/{employeeId}")
    public ResponseEntity<?> getMyOvertime(
            @PathVariable Long employeeId,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year) {

        List<OvertimeResponseDto> result = attendanceService.getMyOvertime(employeeId, month, year);
        String message = result.isEmpty() ? "No overtime found" : "Overtime fetched successfully";
        return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), message, result));
    }

    @Operation(summary = "Manager Overtime View", description = "Manager apne under sab employees ka overtime dekhe")
    @GetMapping("/manager/overtime")
    public ResponseEntity<?> getManagerOvertimeView(@RequestParam Long managerId) {

        List<OvertimeResponseDto> result = attendanceService.getManagerOvertimeView(managerId);
        String message = result.isEmpty() ? "No overtime records" : "Overtime data fetched successfully";
        return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), message, result));
    }
    
    @Operation(summary = "Bulk Approve Attendance", description = "Manager ek saath multiple attendance approve kare")
    @PutMapping("/bulk-approve")
    public ResponseEntity<?> bulkApproveAttendance(@RequestBody BulkApproveRequestDto request) {
        attendanceService.bulkApproveAttendance(request);
        return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(),
                "Attendance bulk approved successfully"));
    }
    
    @Operation(
    	    summary = "SuperAdmin - Today's Attendance Summary",
    	    description = """
    	        Aaj ka attendance summary with pagination.
    	        
    	        📌 filter values:
    	        - No filter   → sabka record (default)
    	        - PUNCHED_IN  → sirf jinhe aaj punch in hua
    	        - PUNCHED_OUT → sirf jinhe aaj punch out bhi hua
    	        
    	        📌 punchStatus values in response:
    	        - NOT_PUNCHED  → aaj aaya hi nahi
    	        - PUNCHED_IN   → punch in hua, punch out pending
    	        - PUNCHED_OUT  → punch in + punch out dono ho gaye
    	        
    	        📌 status values in response:
    	        - PP → Present
    	        - AA → Absent
    	        - HD → Half Day
    	        - H  → Holiday
    	        
    	        📌 approvalStatus values in response:
    	        - PENDING  → manager ne approve nahi kiya
    	        - APPROVED → approved
    	        - REJECTED → rejected
    	    """
    	)
    	@GetMapping("/today/summary")
    	public ResponseEntity<?> getTodayAttendanceSummary(
    	        @RequestParam(defaultValue = "0") int page,
    	        @RequestParam(defaultValue = "10") int size,
    	        @RequestParam(required = false) String filter) {

    	    return ResponseEntity.ok(
    	        new ResponseMessageDto(
    	            HttpStatus.OK.value(),
    	            "Today's attendance summary fetched",
    	            attendanceService.getTodayAttendanceSummary(page, size, filter)
    	        )
    	    );
    	}
    
}