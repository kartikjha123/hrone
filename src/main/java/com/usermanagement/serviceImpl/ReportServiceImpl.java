package com.usermanagement.serviceImpl;


import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.usermanagement.entity.Employee;
import com.usermanagement.repository.AttendanceRepository;
import com.usermanagement.repository.EmployeeRepository;
import com.usermanagement.repository.ProductionEntryRepository;
import com.usermanagement.responseDto.ReportResponseDto;
import com.usermanagement.service.ReportService;

import com.usermanagement.repository.LeaveApplicationRepository;
import com.usermanagement.entity.LeaveApplication;

@Service
public class ReportServiceImpl implements ReportService {

    private final EmployeeRepository employeeRepository;
    private final AttendanceRepository attendanceRepository;
    private final ProductionEntryRepository productionEntryRepository;
 // ✅ Baad mein
    private final LeaveApplicationRepository leaveApplicationRepository;

    public ReportServiceImpl(EmployeeRepository employeeRepository,
                              AttendanceRepository attendanceRepository,
                              ProductionEntryRepository productionEntryRepository,
                              LeaveApplicationRepository leaveApplicationRepository) {
        this.employeeRepository = employeeRepository;
        this.attendanceRepository = attendanceRepository;
        this.productionEntryRepository = productionEntryRepository;
        this.leaveApplicationRepository = leaveApplicationRepository;
    }

    // ─────────────────────────────────────────────
    // ✅ MANAGER REPORT — sirf apne team ka data
    // ─────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public ReportResponseDto getManagerReport(Long managerId,
            Integer month, Integer year) {

        LocalDate now = LocalDate.now();
        int m = month != null ? month : now.getMonthValue();
        int y = year  != null ? year  : now.getYear();

        // Manager find karo
        Employee manager = employeeRepository.findById(managerId)
                .orElseThrow(() -> new RuntimeException("Manager not found"));

        // Manager ke under employees
        List<Employee> team = employeeRepository.findByManagerId(managerId);
        List<Long> teamIds = team.stream()
                .map(Employee::getId)
                .collect(Collectors.toList());

        ReportResponseDto report = new ReportResponseDto();
        report.setReportType("MANAGER_REPORT");
        report.setGeneratedFor(manager.getFirstName() + " " + manager.getLastName());
        report.setPeriod(java.time.Month.of(m).name() + " " + y);
        report.setTotalEmployees(team.size());

        if (teamIds.isEmpty()) {
            report.setEmployeeBreakdown(List.of());
            return report;
        }

        // ── Attendance ──────────────────────────
        report.setPresentToday(
            attendanceRepository.countPunchedInToday(now));
        report.setAbsentToday(
            attendanceRepository.countAbsentToday(now));
        report.setTotalAttendanceThisMonth(
            attendanceRepository.countByEmployeeIdsAndMonth(teamIds, m, y));

        // ── Production ──────────────────────────
        report.setTotalProductionEntries(
            productionEntryRepository.countByEmployeeIdsAndMonth(teamIds, m, y));
        report.setApprovedEntries(
            productionEntryRepository.countByEmployeeIdsStatusAndMonth(teamIds, "APPROVED", m, y));
        report.setPendingEntries(
            productionEntryRepository.countByEmployeeIdsStatusAndMonth(teamIds, "PENDING", m, y));
        report.setRejectedEntries(
            productionEntryRepository.countByEmployeeIdsStatusAndMonth(teamIds, "REJECTED", m, y));
        report.setTotalProductionAmount(
            productionEntryRepository.sumAmountByEmployeeIdsAndMonth(teamIds, m, y));
        report.setApprovedProductionAmount(
            productionEntryRepository.sumAmountByEmployeeIdsStatusAndMonth(teamIds, "APPROVED", m, y));

        // ── Overtime ────────────────────────────
        report.setTotalOvertimeEntries(
            productionEntryRepository.countOvertimeByEmployeeIdsAndMonth(teamIds, m, y));
        report.setTotalOvertimeAmount(
            productionEntryRepository.sumOvertimeAmountByEmployeeIdsAndMonth(teamIds, m, y));

        // ── Leave ───────────────────────────────
        report.setTotalLeaveRequests(
        	    leaveApplicationRepository.countByEmployeeIdsAndMonth(teamIds, m, y));
        	report.setApprovedLeaves(
        	    leaveApplicationRepository.countByEmployeeIdsStatusAndMonth(teamIds, "APPROVED", m, y));
        	report.setPendingLeaves(
        	    leaveApplicationRepository.countByEmployeeIdsStatusAndMonth(teamIds, "PENDING", m, y));

        // ── Employee-wise breakdown ─────────────
        List<Map<String, Object>> breakdown = team.stream().map(emp -> {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("employeeId",   emp.getId());
            row.put("employeeName", emp.getFirstName() + " " + emp.getLastName());
            row.put("employeeCode", emp.getEmployeeCode());
            row.put("department",   emp.getDepartment());

            Long prodEntries = productionEntryRepository
                    .countByEmployeeStatusMonthYear(emp.getId(), null, m, y);
            Double prodAmount = productionEntryRepository
                    .sumAmountByStatusMonthYear(emp.getId(), "APPROVED", m, y);

            row.put("productionEntries", prodEntries != null ? prodEntries : 0);
            row.put("approvedAmount",    prodAmount  != null ? prodAmount  : 0.0);
            return row;
        }).collect(Collectors.toList());

        report.setEmployeeBreakdown(breakdown);
        return report;
    }

    // ─────────────────────────────────────────────
    // ✅ SUPERADMIN REPORT — pure company ka data
    // ─────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public ReportResponseDto getSuperAdminReport(Integer month, Integer year) {

        LocalDate now = LocalDate.now();
        int m = month != null ? month : now.getMonthValue();
        int y = year  != null ? year  : now.getYear();

        ReportResponseDto report = new ReportResponseDto();
        report.setReportType("SUPERADMIN_REPORT");
        report.setGeneratedFor("All Employees");
        report.setPeriod(java.time.Month.of(m).name() + " " + y);

        // ── Employees ───────────────────────────
        report.setTotalEmployees(employeeRepository.count());

        // ── Attendance ──────────────────────────
        report.setPresentToday(
            attendanceRepository.countPunchedInToday(now));
        report.setAbsentToday(
            attendanceRepository.countAbsentToday(now));
        report.setTotalAttendanceThisMonth(
            attendanceRepository.countAllByMonth(m, y));

        // ── Production ──────────────────────────
        List<Object[]> result = productionEntryRepository.findMonthlyTotals(m, y);
        Object[] prodTotals = result.isEmpty() ? new Object[9] : result.get(0);

        report.setTotalProductionEntries(
            prodTotals[0] != null ? ((Number) prodTotals[0]).longValue() : 0L);
        report.setTotalProductionAmount(
            prodTotals[2] != null ? ((Number) prodTotals[2]).doubleValue() : 0.0);
        report.setApprovedEntries(
            prodTotals[3] != null ? ((Number) prodTotals[3]).longValue() : 0L);
        report.setPendingEntries(
            prodTotals[4] != null ? ((Number) prodTotals[4]).longValue() : 0L);
        report.setRejectedEntries(
            prodTotals[5] != null ? ((Number) prodTotals[5]).longValue() : 0L);
        report.setTotalOvertimeEntries(
            prodTotals[6] != null ? ((Number) prodTotals[6]).longValue() : 0L);
        report.setTotalOvertimeAmount(
            prodTotals[8] != null ? ((Number) prodTotals[8]).doubleValue() : 0.0);

        // ── Leave ───────────────────────────────
        report.setTotalLeaveRequests(
        	    leaveApplicationRepository.countAllByMonth(m, y));
        	report.setApprovedLeaves(
        	    leaveApplicationRepository.countAllByStatusAndMonth("APPROVED", m, y));
        	report.setPendingLeaves(
        	    leaveApplicationRepository.countAllByStatusAndMonth("PENDING", m, y));

        // SuperAdmin ko employee breakdown nahi — department-wise dikhao
        List<Map<String, Object>> deptBreakdown =
            productionEntryRepository.findDepartmentWiseSummary(m, y)
                .stream()
                .map(row -> {
                    Map<String, Object> map = new LinkedHashMap<>();
                    map.put("department",   row[0]);
                    map.put("totalEntries", ((Number) row[1]).longValue());
                    map.put("totalAmount",  ((Number) row[2]).doubleValue());
                    return map;
                }).collect(Collectors.toList());

        report.setEmployeeBreakdown(deptBreakdown);
        return report;
    }
}
