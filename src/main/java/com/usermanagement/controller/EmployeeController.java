package com.usermanagement.controller;

import com.usermanagement.entity.Employee;
import com.usermanagement.responseDto.ResponseMessageDto;
import com.usermanagement.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Employee Management", description = "APIs for Employee CRUD operations")
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Operation(summary = "Create Employee")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Employee employee) {
        return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "Employee created successfully", 
                employeeService.createEmployee(employee)));
    }

    @Operation(summary = "Update Employee")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Employee employee) {
        return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "Employee updated successfully", 
                employeeService.updateEmployee(id, employee)));
    }

    @Operation(summary = "Delete Employee")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "Employee deleted successfully"));
    }

    @Operation(summary = "Get All Employees")
    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "Employees fetched successfully", 
                employeeService.getAllEmployees()));
    }

    @Operation(summary = "Get Employee By ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "Employee fetched successfully", 
                employeeService.getEmployeeById(id)));
    }

    @Operation(summary = "Assign Manager to Employee")
    @PutMapping("/{empId}/assign-manager/{managerId}")
    public ResponseEntity<?> assignManager(@PathVariable Long empId, @PathVariable Long managerId) {
        employeeService.assignManager(empId, managerId);
        return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "Manager assigned successfully"));
    }
}
