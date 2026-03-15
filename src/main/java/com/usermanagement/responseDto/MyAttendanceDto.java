package com.usermanagement.responseDto;


import java.util.List;

public class MyAttendanceDto {

    private int totalWorkingDays;
    private int presentDays;
    private int absentDays;
    private int halfDays;
    private String month;

    private List<AttendanceDayDto> records;

    // Getters & Setters
    public int getTotalWorkingDays() { return totalWorkingDays; }
    public void setTotalWorkingDays(int totalWorkingDays) { this.totalWorkingDays = totalWorkingDays; }

    public int getPresentDays() { return presentDays; }
    public void setPresentDays(int presentDays) { this.presentDays = presentDays; }

    public int getAbsentDays() { return absentDays; }
    public void setAbsentDays(int absentDays) { this.absentDays = absentDays; }

    public int getHalfDays() { return halfDays; }
    public void setHalfDays(int halfDays) { this.halfDays = halfDays; }

    public String getMonth() { return month; }
    public void setMonth(String month) { this.month = month; }

    public List<AttendanceDayDto> getRecords() { return records; }
    public void setRecords(List<AttendanceDayDto> records) { this.records = records; }
}