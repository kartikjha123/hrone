package com.usermanagement.responseDto;


public class EmployeeSummaryDto {
    
	private Long employeeId;
    private String employeeName;
    private String employeeCode;
    private Long totalEntries;
    private Double approvedAmount;
    private Double pendingAmount;
    
	public EmployeeSummaryDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EmployeeSummaryDto(Long employeeId, String employeeName, String employeeCode, Long totalEntries,
			Double approvedAmount, Double pendingAmount) {
		super();
		this.employeeId = employeeId;
		this.employeeName = employeeName;
		this.employeeCode = employeeCode;
		this.totalEntries = totalEntries;
		this.approvedAmount = approvedAmount;
		this.pendingAmount = pendingAmount;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public Long getTotalEntries() {
		return totalEntries;
	}

	public void setTotalEntries(Long totalEntries) {
		this.totalEntries = totalEntries;
	}

	public Double getApprovedAmount() {
		return approvedAmount;
	}

	public void setApprovedAmount(Double approvedAmount) {
		this.approvedAmount = approvedAmount;
	}

	public Double getPendingAmount() {
		return pendingAmount;
	}

	public void setPendingAmount(Double pendingAmount) {
		this.pendingAmount = pendingAmount;
	}
    
    
    
    
    
    
}
