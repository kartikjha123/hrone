package com.usermanagement.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usermanagement.requestDto.ApproveLeaveRequestDto;
import com.usermanagement.responseDto.LeaveApplicationResponseDto;
import com.usermanagement.responseDto.ResponseMessageDto;
import com.usermanagement.service.LeaveApprovalService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/manager/leave")
@Tag(name = "Leave Approval", description = "Manager leave approve/reject APIs")
public class LeaveApprovalController {

    private final LeaveApprovalService service;

    public LeaveApprovalController(LeaveApprovalService service) {
        this.service = service;
    }

    @Operation(summary = "Approve or Reject Leave")
    @PostMapping("/approve")
    public ResponseEntity<?> approve(@RequestBody ApproveLeaveRequestDto requestDto) {
        service.approveLeave(requestDto);
        return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(),
                "Leave " + requestDto.getStatus() + " successfully"));
    }

    @Operation(summary = "Pending Leaves — Manager",
               description = "Manager ke under sab pending leaves")
    @GetMapping("/pending/{managerId}")
    public ResponseEntity<?> getPendingLeaves(@PathVariable Long managerId) {
        List<LeaveApplicationResponseDto> list = service.getPendingLeaves(managerId);
        String message = list.isEmpty() ? "No pending leaves" : "Pending leaves fetched";
        return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), message, list));
    }

    @Operation(summary = "All Leaves — Manager",
               description = "Manager ke under sab employees ki saari leaves")
    @GetMapping("/all/{managerId}")
    public ResponseEntity<?> getAllLeavesForManager(@PathVariable Long managerId) {
        List<LeaveApplicationResponseDto> list = service.getAllLeavesForManager(managerId);
        String message = list.isEmpty() ? "No leaves found" : "Leaves fetched successfully";
        return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), message, list));
    }
}
