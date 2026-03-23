package com.usermanagement.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.usermanagement.requestDto.LeaveRequestDto;
import com.usermanagement.responseDto.LeaveApplicationResponseDto;
import com.usermanagement.responseDto.ResponseMessageDto;
import com.usermanagement.service.LeaveApplicationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/employee/leave")
@Tag(name = "Leave Application", description = "Employee leave apply/view APIs")
public class LeaveApplicationController {

    private final LeaveApplicationService service;

    public LeaveApplicationController(LeaveApplicationService service) {
        this.service = service;
    }

    @Operation(summary = "Apply Leave")
    @PostMapping("/apply")
    public ResponseEntity<?> applyLeave(@RequestBody LeaveRequestDto dto) {
        service.applyLeave(dto);
        return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(),
                "Leave Applied Successfully"));
    }

    @Operation(summary = "My All Leaves")
    @GetMapping("/all/{employeeId}")
    public ResponseEntity<?> getAllLeaves(@PathVariable Long employeeId) {
        List<LeaveApplicationResponseDto> list = service.getAllApplyLeaveByEmployee(employeeId);
        String message = list.isEmpty() ? "No leaves found" : "Leave list fetched";
        return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), message, list));
    }

    @Operation(summary = "My Leaves by Status",
               description = "Filter: PENDING / APPROVED / REJECTED")
    @GetMapping("/status/{employeeId}")
    public ResponseEntity<?> getByStatus(@PathVariable Long employeeId,
                                          @RequestParam String status) {
        List<LeaveApplicationResponseDto> list = service.getLeavesByStatus(employeeId, status);
        String message = list.isEmpty() ? "No leaves found" : "Leaves fetched";
        return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), message, list));
    }

    @Operation(summary = "My Leaves by Date Range")
    @GetMapping("/date-range/{employeeId}")
    public ResponseEntity<?> getByDateRange(
            @PathVariable Long employeeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {

        List<LeaveApplicationResponseDto> list = service.getLeavesByDateRange(employeeId, from, to);
        String message = list.isEmpty() ? "No leaves found" : "Leaves fetched";
        return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), message, list));
    }

    @Operation(summary = "Cancel Leave", description = "Sirf PENDING leave cancel ho sakti hai")
    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<?> cancelLeave(@PathVariable Long id) {
        service.cancelLeave(id);
        return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(),
                "Leave Cancelled Successfully"));
    }
}