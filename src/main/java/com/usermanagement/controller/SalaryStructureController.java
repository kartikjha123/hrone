package com.usermanagement.controller;

import com.usermanagement.entity.SalaryStructure;
import com.usermanagement.responseDto.ResponseMessageDto;
import com.usermanagement.service.SalaryStructureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Salary Structure Management", description = "APIs for Salary Structure CRUD operations")
@RestController
@RequestMapping("/api/salary-structure")
public class SalaryStructureController {

    @Autowired
    private SalaryStructureService salaryStructureService;

    @Operation(summary = "Save or Update Salary Structure")
    @PostMapping("/{employeeId}")
    public ResponseEntity<?> saveOrUpdate(@PathVariable Long employeeId, @RequestBody SalaryStructure structure) {
        return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "Salary structure saved successfully", 
                salaryStructureService.saveOrUpdate(employeeId, structure)));
    }

    @Operation(summary = "Get Salary Structure By Employee ID")
    @GetMapping("/{employeeId}")
    public ResponseEntity<?> getByEmployeeId(@PathVariable Long employeeId) {
        return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "Salary structure fetched successfully", 
                salaryStructureService.getByEmployeeId(employeeId)));
    }

    @Operation(summary = "Delete Salary Structure")
    @DeleteMapping("/{employeeId}")
    public ResponseEntity<?> delete(@PathVariable Long employeeId) {
        salaryStructureService.deleteByEmployeeId(employeeId);
        return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "Salary structure deleted successfully"));
    }
}
