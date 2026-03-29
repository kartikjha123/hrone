package com.usermanagement.serviceImpl;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.usermanagement.entity.Attendance;
import com.usermanagement.entity.AttendanceRegularization;
import com.usermanagement.entity.Employee;
import com.usermanagement.repository.AttendanceRegularizationRepository;
import com.usermanagement.repository.AttendanceRepository;
import com.usermanagement.repository.EmployeeRepository;
import com.usermanagement.requestDto.ARApproveDto;
import com.usermanagement.requestDto.ARRequestDto;
import com.usermanagement.responseDto.ARBalanceDto;
import com.usermanagement.responseDto.ARResponseDto;
import com.usermanagement.service.AttendanceRegularizationService;
import com.usermanagement.service.NotificationService;

import jakarta.transaction.Transactional;

@Service
public class AttendanceRegularizationServiceImpl
        implements AttendanceRegularizationService {

    private static final int MAX_AR_PER_MONTH = 6;

    private final AttendanceRegularizationRepository arRepository;
    private final EmployeeRepository employeeRepository;
    private final AttendanceRepository attendanceRepository;
    private final NotificationService notificationService;

    public AttendanceRegularizationServiceImpl(
            AttendanceRegularizationRepository arRepository,
            EmployeeRepository employeeRepository,
            AttendanceRepository attendanceRepository,
            NotificationService notificationService) {
        this.arRepository = arRepository;
        this.employeeRepository = employeeRepository;
        this.attendanceRepository = attendanceRepository;
        this.notificationService = notificationService;
    }

    // ════════════════════════════════════════════════════
    // APPLY AR
    // ════════════════════════════════════════════════════
    @Override
    @Transactional
    public ARResponseDto applyAR(Long employeeId, ARRequestDto request) {

        Employee emp = findEmployeeById(employeeId);

        LocalDate today = LocalDate.now();
        int currentMonth = today.getMonthValue();
        int currentYear  = today.getYear();

        // Step 1: Auto reset if new month
        autoResetARIfNewMonth(emp, currentMonth, currentYear);

        // Step 2: Balance check
        if (emp.getRemainingAR() <= 0) {
            throw new RuntimeException(
                "All attendance regularization requests for this month "
                + "have been exhausted ("
                + MAX_AR_PER_MONTH + "/" + MAX_AR_PER_MONTH + "). "
                + "Please try again next month."
            );
        }

        // Step 3: Future date check
        if (request.getMissingDate().isAfter(today)) {
            throw new RuntimeException(
                "Attendance regularization cannot be applied for a future date. "
                + "Please select today's or a past date."
            );
        }

        // Step 4: Duplicate date check
        arRepository.findActiveByEmployeeAndDate(
                employeeId, request.getMissingDate())
                .ifPresent(existing -> {
                    throw new RuntimeException(
                        "An attendance regularization request already exists "
                        + "for this date: " + request.getMissingDate()
                        + ". You cannot apply twice for the same date."
                    );
                });

        // Step 5: Manager check
        if (emp.getManager() == null) {
            throw new RuntimeException(
                "No manager has been assigned to your profile. "
                + "Please contact HR for assistance."
            );
        }

        // Step 6: Save AR
        AttendanceRegularization ar = new AttendanceRegularization();
        ar.setEmployee(emp);
        ar.setManager(emp.getManager());
        ar.setMissingDate(request.getMissingDate());
        ar.setReason(request.getReason());
        ar.setRequestedPunchIn(request.getRequestedPunchIn());
        ar.setRequestedPunchOut(request.getRequestedPunchOut());
        ar.setStatus("PENDING");
        ar.setArMonth(currentMonth);
        ar.setArYear(currentYear);
        ar.setAppliedAt(LocalDateTime.now());

        AttendanceRegularization saved = arRepository.save(ar);

        // Step 7: Notify manager
        sendNotificationSafely(
            emp.getManager(),
            "New Attendance Regularization Request",
            emp.getFirstName() + " " + emp.getLastName()
                + " has submitted an attendance regularization request "
                + "for " + request.getMissingDate()
                + ". Please review at your earliest convenience.",
            "AR_REQUEST"
        );

        return mapToResponseDto(saved);
    }

    // ════════════════════════════════════════════════════
    // APPROVE / REJECT AR
    // ════════════════════════════════════════════════════
    @Override
    @Transactional
    public void approveAR(Long arId, ARApproveDto dto) {

        AttendanceRegularization ar = arRepository.findById(arId)
                .orElseThrow(() -> new RuntimeException(
                    "Attendance regularization request not found "
                    + "with ID: " + arId
                    + ". Please verify the request ID."
                ));

        // Only PENDING can be approved/rejected
        if (!"PENDING".equals(ar.getStatus())) {
            throw new RuntimeException(
                "Only PENDING attendance regularization requests can be "
                + "approved or rejected. "
                + "Current status of this request is: " + ar.getStatus()
            );
        }

        ar.setStatus(dto.getStatus());
        ar.setManagerRemarks(dto.getRemarks());
        ar.setActionDate(LocalDateTime.now());
        arRepository.save(ar);

        if ("APPROVED".equals(dto.getStatus())) {

            // Deduct remaining AR balance
            Employee emp = ar.getEmployee();
            int newBalance = emp.getRemainingAR() - 1;
            emp.setRemainingAR(Math.max(newBalance, 0));
            employeeRepository.save(emp);

            // Auto mark attendance
            autoMarkAttendance(ar);
        }

        // Notify employee
        String statusText = "APPROVED".equals(dto.getStatus())
            ? "Approved" : "Rejected";

        sendNotificationSafely(
            ar.getEmployee(),
            "Attendance Regularization Request " + statusText,
            "Your attendance regularization request for "
                + ar.getMissingDate() + " has been " + statusText + ". "
                + (dto.getRemarks() != null
                    ? "Manager remarks: " + dto.getRemarks()
                    : "No remarks provided."),
            "AR_STATUS_UPDATE"
        );
    }

    // ════════════════════════════════════════════════════
    // PENDING FOR MANAGER
    // ════════════════════════════════════════════════════
    @Override
    public List<ARResponseDto> getPendingARForManager(Long managerId) {
        return arRepository.findByManagerIdAndStatus(managerId, "PENDING")
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    // ════════════════════════════════════════════════════
    // UPDATE AR
    // ════════════════════════════════════════════════════
    @Override
    @Transactional
    public ARResponseDto updateAR(Long id, ARRequestDto request) {

        AttendanceRegularization ar = arRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                    "Attendance regularization request not found "
                    + "with ID: " + id
                ));

        if (!"PENDING".equals(ar.getStatus())) {
            throw new RuntimeException(
                "Only PENDING attendance regularization requests "
                + "can be updated. "
                + "Current status is: " + ar.getStatus()
            );
        }

        if (request.getMissingDate().isAfter(LocalDate.now())) {
            throw new RuntimeException(
                "Future date cannot be set for attendance regularization. "
                + "Please select today's or a past date."
            );
        }

        ar.setMissingDate(request.getMissingDate());
        ar.setReason(request.getReason());
        ar.setRequestedPunchIn(request.getRequestedPunchIn());
        ar.setRequestedPunchOut(request.getRequestedPunchOut());

        return mapToResponseDto(arRepository.save(ar));
    }

    // ════════════════════════════════════════════════════
    // DELETE AR
    // ════════════════════════════════════════════════════
    @Override
    @Transactional
    public void deleteAR(Long id) {

        AttendanceRegularization ar = arRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                    "Attendance regularization request not found "
                    + "with ID: " + id
                ));

        if (!"PENDING".equals(ar.getStatus())) {
            throw new RuntimeException(
                "Only PENDING attendance regularization requests "
                + "can be deleted. "
                + "Current status is: " + ar.getStatus()
            );
        }

        arRepository.delete(ar);
    }

    // ════════════════════════════════════════════════════
    // AR BALANCE
    // ════════════════════════════════════════════════════
    @Override
    public ARBalanceDto getARBalance(Long employeeId) {

        Employee emp = findEmployeeById(employeeId);
        LocalDate now = LocalDate.now();

        autoResetARIfNewMonth(emp, now.getMonthValue(), now.getYear());

        int remaining = emp.getRemainingAR() != null
            ? emp.getRemainingAR() : MAX_AR_PER_MONTH;
        int used = MAX_AR_PER_MONTH - remaining;

        ARBalanceDto dto = new ARBalanceDto();
        dto.setEmployeeId(employeeId);
        dto.setEmployeeName(
            emp.getFirstName() + " " + emp.getLastName()
        );
        dto.setTotalAR(MAX_AR_PER_MONTH);
        dto.setUsedAR(Math.max(used, 0));
        dto.setRemainingAR(remaining);
        dto.setMonth(now.getMonth().getDisplayName(
            java.time.format.TextStyle.FULL,
            java.util.Locale.ENGLISH) + " " + now.getYear());

        return dto;
    }

    // ════════════════════════════════════════════════════
    // PRIVATE HELPERS
    // ════════════════════════════════════════════════════

    private void autoResetARIfNewMonth(
            Employee emp, int currentMonth, int currentYear) {

        boolean needsReset =
            emp.getArResetMonth() == null
            || emp.getArResetYear() == null
            || emp.getArResetMonth() != currentMonth
            || emp.getArResetYear() != currentYear;

        if (needsReset) {
            emp.setRemainingAR(MAX_AR_PER_MONTH);
            emp.setArResetMonth(currentMonth);
            emp.setArResetYear(currentYear);
            employeeRepository.save(emp);
        }
    }

    private void autoMarkAttendance(AttendanceRegularization ar) {

        Long employeeId = ar.getEmployee().getId();
        LocalDate date  = ar.getMissingDate();

        Attendance attendance = attendanceRepository
                .findByEmployeeIdAndDate(employeeId, date);

        if (attendance == null) {
            attendance = new Attendance();
            attendance.setEmployee(ar.getEmployee());
            attendance.setDate(date);
        }

        if (ar.getRequestedPunchIn() != null)
            attendance.setPunchIn(ar.getRequestedPunchIn());
        if (ar.getRequestedPunchOut() != null)
            attendance.setPunchOut(ar.getRequestedPunchOut());

        attendance.setStatus("PP");
        attendance.setApprovalStatus("APPROVED");

        if (attendance.getPunchIn() != null
                && attendance.getPunchOut() != null) {

            double totalHours = Duration.between(
                attendance.getPunchIn(),
                attendance.getPunchOut()
            ).toMinutes() / 60.0;

            attendance.setTotalHours(
                Math.round(totalHours * 100.0) / 100.0
            );

            double ot = totalHours > 8.0 ? totalHours - 8.0 : 0.0;
            attendance.setOvertimeHours(
                Math.round(ot * 100.0) / 100.0
            );
        }

        attendanceRepository.save(attendance);
    }

    private void sendNotificationSafely(
            Employee recipient, String title,
            String message, String type) {
        try {
            if (recipient != null) {
                notificationService.sendNotification(
                    recipient, title, message, type
                );
            }
        } catch (Exception e) {
            System.out.println(
                "Notification could not be sent: " + e.getMessage()
            );
        }
    }

    private Employee findEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                    "Employee not found with ID: " + id
                    + ". Please provide a valid employee ID."
                ));
    }

    private ARResponseDto mapToResponseDto(AttendanceRegularization ar) {
        ARResponseDto dto = new ARResponseDto();
        dto.setId(ar.getId());
        dto.setEmployeeId(ar.getEmployee().getId());
        dto.setEmployeeName(
            ar.getEmployee().getFirstName() + " "
            + ar.getEmployee().getLastName()
        );
        dto.setEmployeeCode(ar.getEmployee().getEmployeeCode());
        dto.setDepartment(ar.getEmployee().getDepartment());
        dto.setMissingDate(ar.getMissingDate());
        dto.setReason(ar.getReason());
        dto.setStatus(ar.getStatus());
        dto.setManagerRemarks(ar.getManagerRemarks());
        dto.setArMonth(ar.getArMonth());
        dto.setArYear(ar.getArYear());
        dto.setAppliedAt(ar.getAppliedAt() != null
            ? ar.getAppliedAt().toString() : null);
        dto.setActionDate(ar.getActionDate() != null
            ? ar.getActionDate().toString() : null);

        if (ar.getRequestedPunchIn() != null)
            dto.setRequestedPunchIn(
                ar.getRequestedPunchIn().toString()
            );
        if (ar.getRequestedPunchOut() != null)
            dto.setRequestedPunchOut(
                ar.getRequestedPunchOut().toString()
            );

        if (ar.getManager() != null) {
            dto.setManagerName(
                ar.getManager().getFirstName() + " "
                + ar.getManager().getLastName()
            );
        }

        return dto;
    }

    @Override
    public List<ARResponseDto> getEmployeeARHistory(
            Long employeeId, Integer month, Integer year) {
        findEmployeeById(employeeId);
        return arRepository
            .findByEmployeeIdAndMonthYear(employeeId, month, year)
            .stream()
            .map(this::mapToResponseDto)
            .collect(Collectors.toList());
    }
}

