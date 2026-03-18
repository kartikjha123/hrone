package com.usermanagement.responseDto;

import org.springframework.data.domain.Page;

public class MyProductionResponseDto {
    // Summary
    private String employeeName;
    private String employeeCode;
    private Long totalEntries;
    private Long pendingCount;
    private Long approvedCount;
    private Long rejectedCount;
    private Double totalAmount;

    // Entries list
    private Page<ProductionEntryResponseDto> entries;

	public MyProductionResponseDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MyProductionResponseDto(String employeeName, String employeeCode, Long totalEntries, Long pendingCount,
			Long approvedCount, Long rejectedCount, Double totalAmount, Page<ProductionEntryResponseDto> entries) {
		super();
		this.employeeName = employeeName;
		this.employeeCode = employeeCode;
		this.totalEntries = totalEntries;
		this.pendingCount = pendingCount;
		this.approvedCount = approvedCount;
		this.rejectedCount = rejectedCount;
		this.totalAmount = totalAmount;
		this.entries = entries;
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

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Page<ProductionEntryResponseDto> getEntries() {
		return entries;
	}

	public void setEntries(Page<ProductionEntryResponseDto> entries) {
		this.entries = entries;
	}
    
    
    
}
