package com.usermanagement.responseDto;



import java.util.List;
import java.util.Map;

public class ReportResponseDto {

    private String reportType;       // "MANAGER_REPORT" / "SUPERADMIN_REPORT"
    private String generatedFor;     // Manager name ya "All Employees"
    private String period;           // "MARCH 2026"

    // ── Attendance Summary ──────────────────────
    private long totalEmployees;
    private long presentToday;
    private long absentToday;
    private long totalAttendanceThisMonth;

    // ── Production Summary ──────────────────────
    private long totalProductionEntries;
    private long approvedEntries;
    private long pendingEntries;
    private long rejectedEntries;
    private double totalProductionAmount;
    private double approvedProductionAmount;

    // ── Overtime Summary ────────────────────────
    private long totalOvertimeEntries;
    private double totalOvertimeAmount;

    // ── Leave Summary ───────────────────────────
    private long totalLeaveRequests;
    private long approvedLeaves;
    private long pendingLeaves;

    // ── Employee-wise breakdown (Manager ke liye) ──
    private List<Map<String, Object>> employeeBreakdown;

    // Getters & Setters
    public String getReportType() { return reportType; }
    public void setReportType(String reportType) { this.reportType = reportType; }

    public String getGeneratedFor() { return generatedFor; }
    public void setGeneratedFor(String generatedFor) { this.generatedFor = generatedFor; }

    public String getPeriod() { return period; }
    public void setPeriod(String period) { this.period = period; }

    public long getTotalEmployees() { return totalEmployees; }
    public void setTotalEmployees(long totalEmployees) { this.totalEmployees = totalEmployees; }

    public long getPresentToday() { return presentToday; }
    public void setPresentToday(long presentToday) { this.presentToday = presentToday; }

    public long getAbsentToday() { return absentToday; }
    public void setAbsentToday(long absentToday) { this.absentToday = absentToday; }

    public long getTotalAttendanceThisMonth() { return totalAttendanceThisMonth; }
    public void setTotalAttendanceThisMonth(long totalAttendanceThisMonth) {
        this.totalAttendanceThisMonth = totalAttendanceThisMonth;
    }

    public long getTotalProductionEntries() { return totalProductionEntries; }
    public void setTotalProductionEntries(long totalProductionEntries) {
        this.totalProductionEntries = totalProductionEntries;
    }

    public long getApprovedEntries() { return approvedEntries; }
    public void setApprovedEntries(long approvedEntries) { this.approvedEntries = approvedEntries; }

    public long getPendingEntries() { return pendingEntries; }
    public void setPendingEntries(long pendingEntries) { this.pendingEntries = pendingEntries; }

    public long getRejectedEntries() { return rejectedEntries; }
    public void setRejectedEntries(long rejectedEntries) { this.rejectedEntries = rejectedEntries; }

    public double getTotalProductionAmount() { return totalProductionAmount; }
    public void setTotalProductionAmount(double totalProductionAmount) {
        this.totalProductionAmount = totalProductionAmount;
    }

    public double getApprovedProductionAmount() { return approvedProductionAmount; }
    public void setApprovedProductionAmount(double approvedProductionAmount) {
        this.approvedProductionAmount = approvedProductionAmount;
    }

    public long getTotalOvertimeEntries() { return totalOvertimeEntries; }
    public void setTotalOvertimeEntries(long totalOvertimeEntries) {
        this.totalOvertimeEntries = totalOvertimeEntries;
    }

    public double getTotalOvertimeAmount() { return totalOvertimeAmount; }
    public void setTotalOvertimeAmount(double totalOvertimeAmount) {
        this.totalOvertimeAmount = totalOvertimeAmount;
    }

    public long getTotalLeaveRequests() { return totalLeaveRequests; }
    public void setTotalLeaveRequests(long totalLeaveRequests) {
        this.totalLeaveRequests = totalLeaveRequests;
    }

    public long getApprovedLeaves() { return approvedLeaves; }
    public void setApprovedLeaves(long approvedLeaves) { this.approvedLeaves = approvedLeaves; }

    public long getPendingLeaves() { return pendingLeaves; }
    public void setPendingLeaves(long pendingLeaves) { this.pendingLeaves = pendingLeaves; }

    public List<Map<String, Object>> getEmployeeBreakdown() { return employeeBreakdown; }
    public void setEmployeeBreakdown(List<Map<String, Object>> employeeBreakdown) {
        this.employeeBreakdown = employeeBreakdown;
    }
}