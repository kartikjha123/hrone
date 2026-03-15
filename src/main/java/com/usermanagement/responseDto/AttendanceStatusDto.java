package com.usermanagement.responseDto;

import java.time.LocalDate;

public class AttendanceStatusDto {

    private boolean punchedIn;
    private boolean punchedOut;

    private String punchInTime;
    private String punchOutTime;

    // ✅ New fields added
    private String totalHours;
    private LocalDate date;

    private String status;

    public boolean isPunchedIn() { return punchedIn; }
    public void setPunchedIn(boolean punchedIn) { this.punchedIn = punchedIn; }

    public boolean isPunchedOut() { return punchedOut; }
    public void setPunchedOut(boolean punchedOut) { this.punchedOut = punchedOut; }

    public String getPunchInTime() { return punchInTime; }
    public void setPunchInTime(String punchInTime) { this.punchInTime = punchInTime; }

    public String getPunchOutTime() { return punchOutTime; }
    public void setPunchOutTime(String punchOutTime) { this.punchOutTime = punchOutTime; }

    public String getTotalHours() { return totalHours; }
    public void setTotalHours(String totalHours) { this.totalHours = totalHours; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}