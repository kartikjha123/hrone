package com.usermanagement.requestDto;

import java.time.LocalDate;

public class AttendanceRequestDto {
    private Long employeeId;
    private LocalDate date;
    private String status; // PP, AA, H, HD
    private Double overtimeHours;
    private String punchIn;
    private String punchOut;

    // Getters and Setters
    public Long getEmployeeId() { return employeeId; }
    public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Double getOvertimeHours() { return overtimeHours; }
    public void setOvertimeHours(Double overtimeHours) { this.overtimeHours = overtimeHours; }
    public String getPunchIn() { return punchIn; }
    public void setPunchIn(String punchIn) { this.punchIn = punchIn; }
    public String getPunchOut() { return punchOut; }
    public void setPunchOut(String punchOut) { this.punchOut = punchOut; }
}
