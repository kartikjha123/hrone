package com.usermanagement.controller;

import com.usermanagement.entity.Payroll;
import com.usermanagement.repository.PayrollRepository;
import com.usermanagement.responseDto.ResponseMessageDto;
import com.usermanagement.service.PayrollService;
import com.usermanagement.service.PdfService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;

@Tag(name = "Payroll Management", description = "APIs for Salary Processing and Payroll reports")
@RestController
@RequestMapping("/payroll")
public class PayrollController {

    @Autowired
    private PayrollService payrollService;

    @Autowired
    private PdfService pdfService;

    @Autowired
    private PayrollRepository payrollRepository;

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

    @Operation(summary = "Delete Payroll Record", description = "Deletes a payroll record.")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePayroll(@PathVariable Long id) {
        payrollService.deletePayroll(id);
        return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "Payroll record deleted successfully"));
    }

    @Operation(summary = "Download Salary Slip PDF", description = "Generates and downloads a PDF salary slip for the given payroll ID.")
    @GetMapping("/{id}/download")
    public ResponseEntity<InputStreamResource> downloadSalarySlip(@PathVariable Long id) {
        Payroll payroll = payrollRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payroll record not found"));

        ByteArrayInputStream bis = pdfService.generateSalarySlip(payroll);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=salary_slip_" + id + ".pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}
