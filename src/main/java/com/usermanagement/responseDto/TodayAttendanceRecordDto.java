package com.usermanagement.responseDto;



public class TodayAttendanceRecordDto {

    private Long employeeId;
    private String employeeCode;
    private String employeeName;
    private String department;
    private String designation;

    private String punchIn;      // "09:15:00" or null
    private String punchOut;     // "18:30:00" or null
    private String totalHours;   // "9h 15m" or null
    private String status;       // PP / AA / HD / H
    private String approvalStatus; // PENDING / APPROVED / REJECTED
    private String punchStatus;  // "PUNCHED_IN" / "PUNCHED_OUT" / "NOT_PUNCHED"

    // Getters & Setters
    public Long getEmployeeId() { return employeeId; }
    public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }

    public String getEmployeeCode() { return employeeCode; }
    public void setEmployeeCode(String employeeCode) { this.employeeCode = employeeCode; }

    public String getEmployeeName() { return employeeName; }
    public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }

    public String getPunchIn() { return punchIn; }
    public void setPunchIn(String punchIn) { this.punchIn = punchIn; }

    public String getPunchOut() { return punchOut; }
    public void setPunchOut(String punchOut) { this.punchOut = punchOut; }

    public String getTotalHours() { return totalHours; }
    public void setTotalHours(String totalHours) { this.totalHours = totalHours; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getApprovalStatus() { return approvalStatus; }
    public void setApprovalStatus(String approvalStatus) { this.approvalStatus = approvalStatus; }

    public String getPunchStatus() { return punchStatus; }
    public void setPunchStatus(String punchStatus) { this.punchStatus = punchStatus; }
}
