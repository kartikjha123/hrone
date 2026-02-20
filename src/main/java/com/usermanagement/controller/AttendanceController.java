package com.usermanagement.controller;

import com.usermanagement.requestDto.AttendanceRequestDto;
import com.usermanagement.responseDto.ResponseMessageDto;
import com.usermanagement.service.AttendanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Tag(name = "Attendance Management", description = "APIs for Attendance tracking, OT calculation and Grid view")
@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @Operation(summary = "Mark Attendance", description = "Punch in/out or manual attendance entry for an employee.")
    @PostMapping("/mark")
    public ResponseEntity<?> markAttendance(@RequestBody AttendanceRequestDto request) {
        attendanceService.markAttendance(request);
        return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "Attendance marked successfully"));
    }

    @Operation(summary = "Bulk Holiday Mark", description = "Mark a specific date as holiday for all employees or a department.")
    @PostMapping("/bulk-holiday")
    public ResponseEntity<?> bulkHoliday(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) String department) {
        attendanceService.bulkMarkHoliday(date, department);
        return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "Bulk holiday marked successfully"));
    }

    @Operation(summary = "Get Monthly Attendance Grid", description = "Fetch daily attendance records for a specific month (Image 2 logic).")
    @GetMapping("/monthly-grid/{empId}")
    public ResponseEntity<?> getMonthlyGrid(
            @PathVariable Long empId,
            @RequestParam int month,
            @RequestParam int year) {
        return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "Fetched monthly attendance grid", 
                attendanceService.getMonthlyAttendance(empId, month, year)));
    }

    @Operation(summary = "Get Monthly OT Summary", description = "Calculate total overtime hours for an employee for the month.")
    @GetMapping("/ot-summary/{empId}")
    public ResponseEntity<?> getOTSummary(
            @PathVariable Long empId,
            @RequestParam int month,
            @RequestParam int year) {
        return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "Fetched monthly OT summary", 
                attendanceService.calculateMonthlyOT(empId, month, year)));
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
}
