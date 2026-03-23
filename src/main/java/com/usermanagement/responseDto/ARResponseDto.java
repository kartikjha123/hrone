package com.usermanagement.responseDto;

import java.time.LocalDate;

//ARResponseDto.java
public class ARResponseDto {
 private Long id;
 private Long employeeId;
 private String employeeName;
 private String employeeCode;
 private String department;
 private LocalDate missingDate;
 private String reason;
 private String requestedPunchIn;
 private String requestedPunchOut;
 private String status;
 private String managerRemarks;
 private String managerName;
 private Integer arMonth;
 private Integer arYear;
 private String appliedAt;
 private String actionDate;

 public Long getId() { return id; }
 public void setId(Long id) { this.id = id; }
 public Long getEmployeeId() { return employeeId; }
 public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }
 public String getEmployeeName() { return employeeName; }
 public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }
 public String getEmployeeCode() { return employeeCode; }
 public void setEmployeeCode(String employeeCode) { this.employeeCode = employeeCode; }
 public String getDepartment() { return department; }
 public void setDepartment(String department) { this.department = department; }
 public LocalDate getMissingDate() { return missingDate; }
 public void setMissingDate(LocalDate missingDate) { this.missingDate = missingDate; }
 public String getReason() { return reason; }
 public void setReason(String reason) { this.reason = reason; }
 public String getRequestedPunchIn() { return requestedPunchIn; }
 public void setRequestedPunchIn(String requestedPunchIn) { this.requestedPunchIn = requestedPunchIn; }
 public String getRequestedPunchOut() { return requestedPunchOut; }
 public void setRequestedPunchOut(String requestedPunchOut) { this.requestedPunchOut = requestedPunchOut; }
 public String getStatus() { return status; }
 public void setStatus(String status) { this.status = status; }
 public String getManagerRemarks() { return managerRemarks; }
 public void setManagerRemarks(String managerRemarks) { this.managerRemarks = managerRemarks; }
 public String getManagerName() { return managerName; }
 public void setManagerName(String managerName) { this.managerName = managerName; }
 public Integer getArMonth() { return arMonth; }
 public void setArMonth(Integer arMonth) { this.arMonth = arMonth; }
 public Integer getArYear() { return arYear; }
 public void setArYear(Integer arYear) { this.arYear = arYear; }
 public String getAppliedAt() { return appliedAt; }
 public void setAppliedAt(String appliedAt) { this.appliedAt = appliedAt; }
 public String getActionDate() { return actionDate; }
 public void setActionDate(String actionDate) { this.actionDate = actionDate; }
}
