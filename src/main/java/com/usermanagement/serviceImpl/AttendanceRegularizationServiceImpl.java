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

    // ✅ Constructor injection — production best practice
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

        // ✅ Step 1: New month pe auto reset
        autoResetARIfNewMonth(emp, currentMonth, currentYear);

        // ✅ Step 2: Balance check
        if (emp.getRemainingAR() <= 0) {
            throw new RuntimeException(
                "Is mahine ke sare AR use ho gaye hain (" + MAX_AR_PER_MONTH + "/" + MAX_AR_PER_MONTH + ")");
        }

        // ✅ Step 3: Future date check
        if (request.getMissingDate().isAfter(today)) {
            throw new RuntimeException("Future date ke liye AR apply nahi ho sakta");
        }

        // ✅ Step 4: Duplicate date check
        arRepository.findActiveByEmployeeAndDate(employeeId, request.getMissingDate())
                .ifPresent(existing -> {
                    throw new RuntimeException(
                        "Is date ke liye AR already exist karta hai: " + request.getMissingDate());
                });

        // ✅ Step 5: Manager check
        if (emp.getManager() == null) {
            throw new RuntimeException("Employee ka manager assign nahi hai");
        }

        // ✅ Step 6: AR save
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

        // ✅ Step 7: Manager ko notification
        sendNotificationSafely(
            emp.getManager(),
            "Naya AR Request",
            emp.getFirstName() + " " + emp.getLastName()
                + " ne " + request.getMissingDate() + " ke liye AR apply ki hai",
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
                .orElseThrow(() -> new RuntimeException("AR request not found: " + arId));

        // ✅ Sirf PENDING approve/reject ho sakta hai
        if (!"PENDING".equals(ar.getStatus())) {
            throw new RuntimeException(
                "Sirf PENDING AR requests review ho sakti hain. Current status: " + ar.getStatus());
        }

        ar.setStatus(dto.getStatus());
        ar.setManagerRemarks(dto.getRemarks());
        ar.setActionDate(LocalDateTime.now());
        arRepository.save(ar);

        if ("APPROVED".equals(dto.getStatus())) {
            // ✅ RemainingAR ghataao
            Employee emp = ar.getEmployee();
            int newBalance = emp.getRemainingAR() - 1;
            emp.setRemainingAR(Math.max(newBalance, 0)); // negative se bachao
            employeeRepository.save(emp);

            // ✅ Attendance auto mark
            autoMarkAttendance(ar);

        }

        // ✅ Employee ko notification
        sendNotificationSafely(
            ar.getEmployee(),
            "AR Request " + dto.getStatus(),
            ar.getMissingDate() + " ki AR request " + dto.getStatus() + " ho gayi. "
                + (dto.getRemarks() != null ? "Remarks: " + dto.getRemarks() : ""),
            "AR_STATUS_UPDATE"
        );
    }

    // ════════════════════════════════════════════════════
    // GET HISTORY
    // ════════════════════════════════════════════════════
  

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
                .orElseThrow(() -> new RuntimeException("AR not found: " + id));

        if (!"PENDING".equals(ar.getStatus())) {
            throw new RuntimeException("Sirf PENDING AR update ho sakti hai");
        }

        // Future date check
        if (request.getMissingDate().isAfter(LocalDate.now())) {
            throw new RuntimeException("Future date set nahi kar sakte");
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
                .orElseThrow(() -> new RuntimeException("AR not found: " + id));

        if (!"PENDING".equals(ar.getStatus())) {
            throw new RuntimeException("Sirf PENDING AR delete ho sakti hai");
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

        // Auto reset check
        autoResetARIfNewMonth(emp, now.getMonthValue(), now.getYear());

        int remaining = emp.getRemainingAR() != null ? emp.getRemainingAR() : MAX_AR_PER_MONTH;
        int used = MAX_AR_PER_MONTH - remaining;

        ARBalanceDto dto = new ARBalanceDto();
        dto.setEmployeeId(employeeId);
        dto.setEmployeeName(emp.getFirstName() + " " + emp.getLastName());
        dto.setTotalAR(MAX_AR_PER_MONTH);
        dto.setUsedAR(Math.max(used, 0));
        dto.setRemainingAR(remaining);
        dto.setMonth(now.getMonth().getDisplayName(
                java.time.format.TextStyle.FULL, java.util.Locale.ENGLISH) + " " + now.getYear());

        return dto;
    }

    // ════════════════════════════════════════════════════
    // PRIVATE HELPERS
    // ════════════════════════════════════════════════════

    /**
     * New month aane pe AR auto reset karo
     */
    private void autoResetARIfNewMonth(Employee emp, int currentMonth, int currentYear) {
        boolean needsReset = emp.getArResetMonth() == null
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

    /**
     * AR approve hone pe attendance auto create/update karo
     */
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

        // Punch times set karo
        if (ar.getRequestedPunchIn()  != null) attendance.setPunchIn(ar.getRequestedPunchIn());
        if (ar.getRequestedPunchOut() != null) attendance.setPunchOut(ar.getRequestedPunchOut());

        attendance.setStatus("PP");
        attendance.setApprovalStatus("APPROVED");

        // Hours recalculate
        if (attendance.getPunchIn() != null && attendance.getPunchOut() != null) {
            double totalHours = Duration.between(
                    attendance.getPunchIn(), attendance.getPunchOut()
            ).toMinutes() / 60.0;

            attendance.setTotalHours(Math.round(totalHours * 100.0) / 100.0);

            double ot = totalHours > 8.0 ? totalHours - 8.0 : 0.0;
            attendance.setOvertimeHours(Math.round(ot * 100.0) / 100.0);
        }

        attendanceRepository.save(attendance);
    }

    /**
     * Notification safely bhejo — agar fail bhi ho to main flow na tute
     */
    private void sendNotificationSafely(Employee recipient, String title,
                                         String message, String type) {
        try {
            if (recipient != null) {
                notificationService.sendNotification(recipient, title, message, type);
            }
        } catch (Exception e) {
        	System.out.println(e);
            // Main flow continue rahega
        }
    }

    private Employee findEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found: " + id));
    }

    /**
     * Entity -> ResponseDto mapper
     */
    private ARResponseDto mapToResponseDto(AttendanceRegularization ar) {
        ARResponseDto dto = new ARResponseDto();
        dto.setId(ar.getId());
        dto.setEmployeeId(ar.getEmployee().getId());
        dto.setEmployeeName(ar.getEmployee().getFirstName() + " " + ar.getEmployee().getLastName());
        dto.setEmployeeCode(ar.getEmployee().getEmployeeCode());
        dto.setDepartment(ar.getEmployee().getDepartment());
        dto.setMissingDate(ar.getMissingDate());
        dto.setReason(ar.getReason());
        dto.setStatus(ar.getStatus());
        dto.setManagerRemarks(ar.getManagerRemarks());
        dto.setArMonth(ar.getArMonth());
        dto.setArYear(ar.getArYear());
        dto.setAppliedAt(ar.getAppliedAt() != null ? ar.getAppliedAt().toString() : null);
        dto.setActionDate(ar.getActionDate() != null ? ar.getActionDate().toString() : null);

        if (ar.getRequestedPunchIn()  != null) dto.setRequestedPunchIn(ar.getRequestedPunchIn().toString());
        if (ar.getRequestedPunchOut() != null) dto.setRequestedPunchOut(ar.getRequestedPunchOut().toString());

        if (ar.getManager() != null) {
            dto.setManagerName(ar.getManager().getFirstName() + " " + ar.getManager().getLastName());
        }

        return dto;
    }
    
    @Override
    public List<ARResponseDto> getEmployeeARHistory(Long employeeId, Integer month, Integer year) {
        findEmployeeById(employeeId);

        return arRepository.findByEmployeeIdAndMonthYear(employeeId, month, year)
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }
}