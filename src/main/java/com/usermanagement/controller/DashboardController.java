package com.usermanagement.controller;

import com.usermanagement.entity.*;
import com.usermanagement.repository.*;
import com.usermanagement.responseDto.ResponseMessageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "ESS Dashboard", description = "APIs for Employee Self-Service landing page")
@RestController
@RequestMapping("/api/ess/dashboard")
public class DashboardController {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private LeaveBalanceRepository leaveBalanceRepository;

    @Autowired
    private PayrollRepository payrollRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Operation(summary = "Get Employee Dashboard Summary", description = "Consolidated view of attendance, leave balance, and recent payslips.")
    @GetMapping("/{empId}")
    public ResponseEntity<?> getDashboard(@PathVariable Long empId) {
        Map<String, Object> dashboard = new HashMap<>();
        
        // 1. Attendance Summary (Current Month)
        LocalDate now = LocalDate.now();
        List<Attendance> attendance = attendanceRepository.findByEmployeeIdAndDateBetween(empId, now.withDayOfMonth(1), now);
        dashboard.put("monthlyAttendanceCount", attendance.size());

        // 2. Leave Balances
        List<LeaveBalance> balances = leaveBalanceRepository.findByEmployeeId(empId);
        dashboard.put("leaveBalances", balances);

        // 3. Last Payslip
        payrollRepository.findByMonthAndYear(now.minusMonths(1).getMonthValue(), now.minusMonths(1).getYear())
                .stream().filter(p -> p.getEmployee().getId().equals(empId)).findFirst()
                .ifPresent(p -> dashboard.put("lastPayslip", p));

        // 4. Upcoming Holidays (Placeholder)
        dashboard.put("upcomingHolidays", List.of(
            Map.of("date", "2025-01-26", "name", "Republic Day"),
            Map.of("date", "2025-08-15", "name", "Independence Day")
        ));

        return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "Dashboard data fetched", dashboard));
    }
}
