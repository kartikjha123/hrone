package com.usermanagement.requestDto;

public class ProductionEntryRequestDto {

	private Long employeeId;
	private Long itemId;
	private Integer quantity;
	
	private Boolean isOvertime = false;
	
	public ProductionEntryRequestDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProductionEntryRequestDto(Long employeeId, Long itemId, Integer quantity) {
		super();
		this.employeeId = employeeId;
		this.itemId = itemId;
		this.quantity = quantity;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	
	
	public Boolean getIsOvertime() {
	    return isOvertime;
	}
	public void setIsOvertime(Boolean isOvertime) {
	    this.isOvertime = isOvertime;
	}
	
}
