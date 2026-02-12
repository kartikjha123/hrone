package com.usermanagement.controller;

import com.usermanagement.responseDto.ResponseMessageDto;
import com.usermanagement.service.PayrollService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Payroll Management", description = "APIs for Salary Processing and Payroll reports")
@RestController
@RequestMapping("/payroll")
public class PayrollController {

    @Autowired
    private PayrollService payrollService;

    @Operation(summary = "Process Monthly Payroll", description = "Calculate net salary including production, attendance, OT and advance deductions.")
    @PostMapping("/process")
    public ResponseEntity<?> processPayroll(
            @RequestParam Long employeeId,
            @RequestParam int month,
            @RequestParam int year) {
        return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "Payroll processed successfully", 
                payrollService.processPayroll(employeeId, month, year)));
    }

    @Operation(summary = "Get Monthly Payroll Report", description = "Fetch all payroll records for a specific month.")
    @GetMapping("/report")
    public ResponseEntity<?> getReport(
            @RequestParam int month,
            @RequestParam int year) {
        return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "Fetched monthly payroll report", 
                payrollService.getMonthlyPayrollReport(month, year)));
    }

    @Operation(summary = "Update Payroll Status", description = "Mark payroll as PAID or APPROVED.")
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        payrollService.updatePayrollStatus(id, status);
        return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "Payroll status updated successfully"));
    }
}
