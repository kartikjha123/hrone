package com.usermanagement.responseDto;


import java.util.List;

public class DashboardResponseDto {

    private int monthlyAttendanceCount;
    private List<LeaveBalanceDto> leaveBalances;
    private Object lastPayslip;
    private List<Object> upcomingHolidays;

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
    public List<Object> getUpcomingHolidays() { return upcomingHolidays; }
    public void setUpcomingHolidays(List<Object> upcomingHolidays) {
        this.upcomingHolidays = upcomingHolidays;
    }
}