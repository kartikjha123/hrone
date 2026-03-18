package com.usermanagement.responseDto;

import java.util.List;

public class MonthlyProductionReportDto {
	
	
	private String employeeName;
    private String month;
    private Long totalEntries;
    private Integer totalQuantity;
    private Double totalAmount;
    private Double approvedAmount;
    private Double pendingAmount;
    private Double rejectedAmount;
    private List<ProductionEntryResponseDto> dailyEntries;
    
    
	public MonthlyProductionReportDto() {
		super();
		// TODO Auto-generated constructor stub
	}


	public MonthlyProductionReportDto(String employeeName, String month, Long totalEntries, Integer totalQuantity,
			Double totalAmount, Double approvedAmount, Double pendingAmount, Double rejectedAmount,
			List<ProductionEntryResponseDto> dailyEntries) {
		super();
		this.employeeName = employeeName;
		this.month = month;
		this.totalEntries = totalEntries;
		this.totalQuantity = totalQuantity;
		this.totalAmount = totalAmount;
		this.approvedAmount = approvedAmount;
		this.pendingAmount = pendingAmount;
		this.rejectedAmount = rejectedAmount;
		this.dailyEntries = dailyEntries;
	}


	public String getEmployeeName() {
		return employeeName;
	}


	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}


	public String getMonth() {
		return month;
	}


	public void setMonth(String month) {
		this.month = month;
	}


	public Long getTotalEntries() {
		return totalEntries;
	}


	public void setTotalEntries(Long totalEntries) {
		this.totalEntries = totalEntries;
	}


	public Integer getTotalQuantity() {
		return totalQuantity;
	}


	public void setTotalQuantity(Integer totalQuantity) {
		this.totalQuantity = totalQuantity;
	}


	public Double getTotalAmount() {
		return totalAmount;
	}


	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
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


	public Double getRejectedAmount() {
		return rejectedAmount;
	}


	public void setRejectedAmount(Double rejectedAmount) {
		this.rejectedAmount = rejectedAmount;
	}


	public List<ProductionEntryResponseDto> getDailyEntries() {
		return dailyEntries;
	}


	public void setDailyEntries(List<ProductionEntryResponseDto> dailyEntries) {
		this.dailyEntries = dailyEntries;
	}
    
    
    
    
    
    
	
	

}
