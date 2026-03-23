package com.usermanagement.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.usermanagement.entity.LeaveApplication;
import com.usermanagement.entity.LeaveBalance;
import com.usermanagement.repository.LeaveApplicationRepository;
import com.usermanagement.repository.LeaveBalanceRepository;
import com.usermanagement.requestDto.ApproveLeaveRequestDto;
import com.usermanagement.responseDto.LeaveApplicationResponseDto;
import com.usermanagement.service.LeaveApprovalService;

import jakarta.transaction.Transactional;

@Service
public class LeaveApprovalServiceImpl implements LeaveApprovalService {

    private final LeaveApplicationRepository leaveApplicationRepository;
    private final LeaveBalanceRepository leaveBalanceRepository;

    public LeaveApprovalServiceImpl(LeaveApplicationRepository leaveApplicationRepository,
                                     LeaveBalanceRepository leaveBalanceRepository) {
        this.leaveApplicationRepository = leaveApplicationRepository;
        this.leaveBalanceRepository = leaveBalanceRepository;
    }

    // ════════════════════════════════════════════════
    // APPROVE / REJECT
    // ════════════════════════════════════════════════
    @Override
    @Transactional
    public void approveLeave(ApproveLeaveRequestDto requestDto) {

        LeaveApplication app = leaveApplicationRepository
                .findById(requestDto.getLeaveApplicationId())
                .orElseThrow(() -> new RuntimeException("Leave application not found"));

        // ✅ Sirf PENDING approve/reject ho sakti hai
        if (!"PENDING".equals(app.getStatus())) {
            throw new RuntimeException(
                "Sirf PENDING leave approve/reject ho sakti hai. Current: " + app.getStatus());
        }

        // ✅ Status validate karo
        if (!"APPROVED".equals(requestDto.getStatus()) && !"REJECTED".equals(requestDto.getStatus())) {
            throw new RuntimeException("Status sirf APPROVED ya REJECTED hona chahiye");
        }

        app.setStatus(requestDto.getStatus());
        app.setManagerComment(requestDto.getComment());
        app.setActionDate(LocalDateTime.now());

        // ✅ Sirf APPROVE pe balance ghataao
        if ("APPROVED".equals(requestDto.getStatus())) {
            LeaveBalance balance = leaveBalanceRepository
                    .findByEmployeeIdAndLeaveTypeIdAndYear(
                            app.getEmployee().getId(),
                            app.getLeaveType().getId(),
                            app.getFromDate().getYear())
                    .orElseThrow(() -> new RuntimeException("Leave balance not found"));

            // ✅ Double check balance
            if (balance.getRemainingLeaves() < app.getTotalDays()) {
                throw new RuntimeException("Employee ka leave balance insufficient hai");
            }

            balance.setUsedLeaves(balance.getUsedLeaves() + app.getTotalDays());
            balance.setRemainingLeaves(balance.getRemainingLeaves() - app.getTotalDays());
            leaveBalanceRepository.save(balance);
        }

        leaveApplicationRepository.save(app);
    }

    // ════════════════════════════════════════════════
    // PENDING LEAVES — MANAGER
    // ════════════════════════════════════════════════
    @Override
    public List<LeaveApplicationResponseDto> getPendingLeaves(Long managerId) {
        return leaveApplicationRepository
                .findByManagerIdAndStatus(managerId, "PENDING")
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // ════════════════════════════════════════════════
    // ALL LEAVES — MANAGER (sab status)
    // ════════════════════════════════════════════════
    @Override
    public List<LeaveApplicationResponseDto> getAllLeavesForManager(Long managerId) {
        return leaveApplicationRepository
                .findByManagerId(managerId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // ════════════════════════════════════════════════
    // MAPPER
    // ════════════════════════════════════════════════
    private LeaveApplicationResponseDto mapToDto(LeaveApplication leave) {
        LeaveApplicationResponseDto dto = new LeaveApplicationResponseDto();
        dto.setId(leave.getId());
        dto.setEmployeeName(leave.getEmployee().getFirstName()
                + " " + leave.getEmployee().getLastName());
        dto.setLeaveType(leave.getLeaveType().getName());
        dto.setFromDate(leave.getFromDate());
        dto.setToDate(leave.getToDate());
        dto.setTotalDays(leave.getTotalDays());
        dto.setReason(leave.getReason());
        dto.setStatus(leave.getStatus());
        dto.setManagerComment(leave.getManagerComment());
        return dto;
    }
}





