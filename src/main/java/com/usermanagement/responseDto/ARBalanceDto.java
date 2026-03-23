package com.usermanagement.responseDto;

//ARBalanceDto.java
public class ARBalanceDto {
 private Long employeeId;
 private String employeeName;
 private int totalAR;
 private int usedAR;
 private int remainingAR;
 private String month;

 public Long getEmployeeId() { return employeeId; }
 public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }
 public String getEmployeeName() { return employeeName; }
 public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }
 public int getTotalAR() { return totalAR; }
 public void setTotalAR(int totalAR) { this.totalAR = totalAR; }
 public int getUsedAR() { return usedAR; }
 public void setUsedAR(int usedAR) { this.usedAR = usedAR; }
 public int getRemainingAR() { return remainingAR; }
 public void setRemainingAR(int remainingAR) { this.remainingAR = remainingAR; }
 public String getMonth() { return month; }
 public void setMonth(String month) { this.month = month; }
}
