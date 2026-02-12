package com.usermanagement.controller;

import com.usermanagement.requestDto.AdvanceSalaryRequestDto;
import com.usermanagement.responseDto.ResponseMessageDto;
import com.usermanagement.service.AdvanceSalaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Advance Salary Management", description = "APIs for Advance Salary requests and approvals")
@RestController
@RequestMapping("/advance-salary")
public class AdvanceSalaryController {

    @Autowired
    private AdvanceSalaryService advanceSalaryService;

    @Operation(summary = "Request Advance Salary", description = "Employee submits a request for advance salary.")
    @PostMapping("/request")
    public ResponseEntity<?> requestAdvance(@RequestBody AdvanceSalaryRequestDto request) {
        advanceSalaryService.requestAdvance(request);
        return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "Advance salary request submitted successfully"));
    }

    @Operation(summary = "Approve/Reject Advance Salary", description = "Admin approves or rejects an advance salary request.")
    @PutMapping("/approve/{id}")
    public ResponseEntity<?> approveAdvance(
            @PathVariable Long id,
            @RequestParam Long adminId,
            @RequestParam String status,
            @RequestParam(required = false) String comments) {
        advanceSalaryService.approveAdvance(id, adminId, status, comments);
        return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "Advance salary status updated successfully"));
    }

    @Operation(summary = "Get Employee Advances", description = "Fetch all advance requests for a specific employee.")
    @GetMapping("/employee/{empId}")
    public ResponseEntity<?> getEmployeeAdvances(@PathVariable Long empId) {
        return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "Fetched employee advances", advanceSalaryService.getEmployeeAdvances(empId)));
    }

    @Operation(summary = "Get Pending Advances", description = "Fetch all pending advance requests for Admin review.")
    @GetMapping("/pending")
    public ResponseEntity<?> getPendingAdvances() {
        return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "Fetched pending advances", advanceSalaryService.getAllPendingAdvances()));
    }
}
