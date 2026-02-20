package com.usermanagement.responseDto;


import java.time.LocalDate;

public class ProductionEntryResponseDto {

    private Long productionId;

    private Long employeeId;
    private String employeeName;
    private String employeeCode;

    private Long itemId;
    private String itemName;
    private Double rate;
    private String unit;

    private LocalDate workDate;
    private Integer quantity;
    private Double amount;
    private String status;
	public ProductionEntryResponseDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ProductionEntryResponseDto(Long productionId, Long employeeId, String employeeName, String employeeCode,
			Long itemId, String itemName, Double rate, String unit, LocalDate workDate, Integer quantity,
			Double amount, String status) {
		super();
		this.productionId = productionId;
		this.employeeId = employeeId;
		this.employeeName = employeeName;
		this.employeeCode = employeeCode;
		this.itemId = itemId;
		this.itemName = itemName;
		this.rate = rate;
		this.unit = unit;
		this.workDate = workDate;
		this.quantity = quantity;
		this.amount = amount;
		this.status = status;
	}
	public Long getProductionId() {
		return productionId;
	}
	public void setProductionId(Long productionId) {
		this.productionId = productionId;
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
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public LocalDate getWorkDate() {
		return workDate;
	}
	public void setWorkDate(LocalDate workDate) {
		this.workDate = workDate;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
    
    
    

    // getters & setters
}

