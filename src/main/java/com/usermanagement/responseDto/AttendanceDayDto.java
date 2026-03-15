package com.usermanagement.responseDto;


import java.time.LocalDate;

public class AttendanceDayDto {

    private LocalDate date;
    private String status;       // PP, AA, H, HD
    private String punchIn;
    private String punchOut;
    private String totalHours;
    private String approvalStatus;

    // Getters & Setters
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPunchIn() { return punchIn; }
    public void setPunchIn(String punchIn) { this.punchIn = punchIn; }

    public String getPunchOut() { return punchOut; }
    public void setPunchOut(String punchOut) { this.punchOut = punchOut; }

    public String getTotalHours() { return totalHours; }
    public void setTotalHours(String totalHours) { this.totalHours = totalHours; }

    public String getApprovalStatus() { return approvalStatus; }
    public void setApprovalStatus(String approvalStatus) { this.approvalStatus = approvalStatus; }
}