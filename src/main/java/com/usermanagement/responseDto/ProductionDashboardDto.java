package com.usermanagement.responseDto;

public class ProductionDashboardDto {

	private String employeeName;
	private String employeeCode;
	private Long totalThisMonth;
	private Long pendingCount;
	private Long approvedCount;
	private Long rejectedCount;
	private Double approvedAmount;
	private Double pendingAmount;
	private String month;
	private Integer year;
	
	
	public ProductionDashboardDto() {
		super();
		// TODO Auto-generated constructor stub
	}


	public ProductionDashboardDto(String employeeName, String employeeCode, Long totalThisMonth, Long pendingCount,
			Long approvedCount, Long rejectedCount, Double approvedAmount, Double pendingAmount, String month,
			Integer year) {
		super();
		this.employeeName = employeeName;
		this.employeeCode = employeeCode;
		this.totalThisMonth = totalThisMonth;
		this.pendingCount = pendingCount;
		this.approvedCount = approvedCount;
		this.rejectedCount = rejectedCount;
		this.approvedAmount = approvedAmount;
		this.pendingAmount = pendingAmount;
		this.month = month;
		this.year = year;
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


	public Long getTotalThisMonth() {
		return totalThisMonth;
	}


	public void setTotalThisMonth(Long totalThisMonth) {
		this.totalThisMonth = totalThisMonth;
	}


	public Long getPendingCount() {
		return pendingCount;
	}


	public void setPendingCount(Long pendingCount) {
		this.pendingCount = pendingCount;
	}


	public Long getApprovedCount() {
		return approvedCount;
	}


	public void setApprovedCount(Long approvedCount) {
		this.approvedCount = approvedCount;
	}


	public Long getRejectedCount() {
		return rejectedCount;
	}


	public void setRejectedCount(Long rejectedCount) {
		this.rejectedCount = rejectedCount;
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


	public String getMonth() {
		return month;
	}


	public void setMonth(String month) {
		this.month = month;
	}


	public Integer getYear() {
		return year;
	}


	public void setYear(Integer year) {
		this.year = year;
	}
	
	
	

}
