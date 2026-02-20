package com.usermanagement.controller;

import com.usermanagement.entity.AttendanceRegularization;
import com.usermanagement.responseDto.ResponseMessageDto;
import com.usermanagement.service.AttendanceRegularizationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Attendance Regularization (AR)", description = "APIs for marking attendance when face detection is missed")
@RestController
@RequestMapping("/api/attendance/ar")
public class AttendanceRegularizationController {

    @Autowired
    private AttendanceRegularizationService arService;

    @Operation(summary = "Apply for AR", description = "Employee applies for AR if they missed face detection. Limit: 6 per cycle.")
    @PostMapping("/apply")
    public ResponseEntity<?> apply(@RequestParam Long employeeId, @RequestBody AttendanceRegularization request) {
        return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "AR Request submitted", 
                arService.applyAR(employeeId, request)));
    }

    @Operation(summary = "Approve/Reject AR", description = "Manager approves or rejects the AR request.")
    @PutMapping("/approve/{id}")
    public ResponseEntity<?> approve(@PathVariable Long id, @RequestParam String status, @RequestParam(required = false) String comment) {
        arService.approveAR(id, status, comment);
        return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "AR Request " + status));
    }

    @Operation(summary = "Get My AR History", description = "Employee can see their previous AR requests and status.")
    @GetMapping("/history/{empId}")
    public ResponseEntity<?> getHistory(@PathVariable Long empId) {
        return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "Fetched AR history", 
                arService.getEmployeeARHistory(empId)));
    }

    @Operation(summary = "Get Pending AR for Manager", description = "Fetch all pending AR requests for a manager's team.")
    @GetMapping("/pending/{managerId}")
    public ResponseEntity<?> getPending(@PathVariable Long managerId) {
        return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "Fetched pending AR requests", arService.getPendingARForManager(managerId)));
    }

    @Operation(summary = "Update Attendance Regularization Request", description = "Updates an existing pending AR request")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateAR(@PathVariable Long id, @RequestBody AttendanceRegularization request) {
        return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "AR request updated successfully", arService.updateAR(id, request)));
    }

    @Operation(summary = "Delete Attendance Regularization Request", description = "Deletes a pending AR request")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAR(@PathVariable Long id) {
        arService.deleteAR(id);
        return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "AR request deleted successfully"));
    }
}
