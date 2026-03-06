package com.usermanagement.responseDto;

import java.time.LocalDate;

public class PayrollResponseDto {

	private Long id;
	private Long employeeId;
	private String employeeName;
	private Integer month;
	private Integer year;
	private Double productionEarnings;
	private Double baseSalary;
	private Double otEarnings;
	private Double advanceDeduction;
	private Double netSalary;
	private LocalDate processedDate;
	private String status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Double getProductionEarnings() {
		return productionEarnings;
	}

	public void setProductionEarnings(Double productionEarnings) {
		this.productionEarnings = productionEarnings;
	}

	public Double getBaseSalary() {
		return baseSalary;
	}

	public void setBaseSalary(Double baseSalary) {
		this.baseSalary = baseSalary;
	}

	public Double getOtEarnings() {
		return otEarnings;
	}

	public void setOtEarnings(Double otEarnings) {
		this.otEarnings = otEarnings;
	}

	public Double getAdvanceDeduction() {
		return advanceDeduction;
	}

	public void setAdvanceDeduction(Double advanceDeduction) {
		this.advanceDeduction = advanceDeduction;
	}

	public Double getNetSalary() {
		return netSalary;
	}

	public void setNetSalary(Double netSalary) {
		this.netSalary = netSalary;
	}

	public LocalDate getProcessedDate() {
		return processedDate;
	}

	public void setProcessedDate(LocalDate processedDate) {
		this.processedDate = processedDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}