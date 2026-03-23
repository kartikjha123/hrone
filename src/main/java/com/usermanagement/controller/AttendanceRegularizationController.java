package com.usermanagement.controller;

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

import com.usermanagement.requestDto.ARApproveDto;
import com.usermanagement.requestDto.ARRequestDto;
import com.usermanagement.responseDto.ResponseMessageDto;
import com.usermanagement.service.AttendanceRegularizationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Attendance Regularization (AR)",
description = "Employee AR apply, Manager approve, Balance check")
@RestController
@RequestMapping("/api/attendance/ar")
public class AttendanceRegularizationController {

private final AttendanceRegularizationService arService;

public AttendanceRegularizationController(AttendanceRegularizationService arService) {
   this.arService = arService;
}

@Operation(summary = "Apply AR", description = "Employee AR apply kare — max 6 per month")
@PostMapping("/apply/{employeeId}")
public ResponseEntity<?> apply(@PathVariable Long employeeId,
                              @Valid @RequestBody ARRequestDto request) {
   return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(),
           "AR Request submitted successfully",
           arService.applyAR(employeeId, request)));
}

@Operation(summary = "Approve / Reject AR", description = "Manager AR approve ya reject kare")
@PutMapping("/review/{arId}")
public ResponseEntity<?> review(@PathVariable Long arId,
                               @Valid @RequestBody ARApproveDto dto) {
   arService.approveAR(arId, dto);
   return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(),
           "AR Request " + dto.getStatus() + " successfully"));
}

@Operation(summary = "AR Balance", description = "Employee ka is month kitna AR bacha hai")
@GetMapping("/balance/{employeeId}")
public ResponseEntity<?> getBalance(@PathVariable Long employeeId) {
   return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(),
           "AR balance fetched",
           arService.getARBalance(employeeId)));
}

//@Operation(summary = "My AR History", description = "Employee apni saari AR requests dekhe")
//@GetMapping("/history/{employeeId}")
//public ResponseEntity<?> getHistory(@PathVariable Long employeeId) {
//   return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(),
//           "AR history fetched",
//           arService.getEmployeeARHistory(employeeId)));
//}

@Operation(summary = "Pending AR — Manager", description = "Manager apne team ki pending AR dekhe")
@GetMapping("/pending/{managerId}")
public ResponseEntity<?> getPending(@PathVariable Long managerId) {
   return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(),
           "Pending AR fetched",
           arService.getPendingARForManager(managerId)));
}

@Operation(summary = "Update AR", description = "Sirf PENDING AR update ho sakti hai")
@PutMapping("/update/{id}")
public ResponseEntity<?> update(@PathVariable Long id,
                               @Valid @RequestBody ARRequestDto request) {
   return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(),
           "AR updated successfully",
           arService.updateAR(id, request)));
}

@Operation(summary = "Delete AR", description = "Sirf PENDING AR delete ho sakti hai")
@DeleteMapping("/delete/{id}")
public ResponseEntity<?> delete(@PathVariable Long id) {
   arService.deleteAR(id);
   return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(),
           "AR deleted successfully"));
}

@Operation(summary = "My AR History", description = "Month/Year filter optional hai")
@GetMapping("/history/{employeeId}")
public ResponseEntity<?> getHistory(
        @PathVariable Long employeeId,
        @RequestParam(required = false) Integer month,
        @RequestParam(required = false) Integer year) {

    return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(),
            "AR history fetched",
            arService.getEmployeeARHistory(employeeId, month, year)));
}
}


