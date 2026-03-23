package com.usermanagement.responseDto;

//OvertimeResponseDto.java
public class OvertimeResponseDto {
 private Long attendanceId;
 private Long employeeId;
 private String employeeName;
 private String employeeCode;
 private String department;
 private String date;
 private String punchIn;
 private String punchOut;
 private String totalHours;       // "9h 30m"
 private Double overtimeHours;    // 1.5
 private String approvalStatus;

 // Getters & Setters
 public Long getAttendanceId() { return attendanceId; }
 public void setAttendanceId(Long attendanceId) { this.attendanceId = attendanceId; }
 public Long getEmployeeId() { return employeeId; }
 public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }
 public String getEmployeeName() { return employeeName; }
 public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }
 public String getEmployeeCode() { return employeeCode; }
 public void setEmployeeCode(String employeeCode) { this.employeeCode = employeeCode; }
 public String getDepartment() { return department; }
 public void setDepartment(String department) { this.department = department; }
 public String getDate() { return date; }
 public void setDate(String date) { this.date = date; }
 public String getPunchIn() { return punchIn; }
 public void setPunchIn(String punchIn) { this.punchIn = punchIn; }
 public String getPunchOut() { return punchOut; }
 public void setPunchOut(String punchOut) { this.punchOut = punchOut; }
 public String getTotalHours() { return totalHours; }
 public void setTotalHours(String totalHours) { this.totalHours = totalHours; }
 public Double getOvertimeHours() { return overtimeHours; }
 public void setOvertimeHours(Double overtimeHours) { this.overtimeHours = overtimeHours; }

 public String getApprovalStatus() { return approvalStatus; }
 public void setApprovalStatus(String approvalStatus) { this.approvalStatus = approvalStatus; }
}