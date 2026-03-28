package com.usermanagement.responseDto;


import java.util.List;
import java.util.Map;

public class DashboardResponseDto {

    private int monthlyAttendanceCount;
    private List<LeaveBalanceDto> leaveBalances;
    private Object lastPayslip;
    private List<Map<String, String>> upcomingHolidays;

    // Getters & Setters
    public int getMonthlyAttendanceCount() { return monthlyAttendanceCount; }
    public void setMonthlyAttendanceCount(int monthlyAttendanceCount) {
        this.monthlyAttendanceCount = monthlyAttendanceCount;
    }
    public List<LeaveBalanceDto> getLeaveBalances() { return leaveBalances; }
    public void setLeaveBalances(List<LeaveBalanceDto> leaveBalances) {
        this.leaveBalances = leaveBalances;
    }
    public Object getLastPayslip() { return lastPayslip; }
    public void setLastPayslip(Object lastPayslip) { this.lastPayslip = lastPayslip; }
    public List<Map<String, String>> getUpcomingHolidays() { return upcomingHolidays; }
    public void setUpcomingHolidays(List<Map<String, String>> upcomingHolidays2) {
        this.upcomingHolidays = upcomingHolidays2;
    }
}