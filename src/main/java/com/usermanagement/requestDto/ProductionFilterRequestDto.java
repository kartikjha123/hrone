package com.usermanagement.requestDto;

import java.time.LocalDate;

public class ProductionFilterRequestDto {

	private Long employeeId; // optional
	private Long itemId; // optional
	private LocalDate fromDate; // optional
	private LocalDate toDate; // optional
	
	private Integer page;
	private Integer size;
	
	public ProductionFilterRequestDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProductionFilterRequestDto(Long employeeId, Long itemId, LocalDate fromDate, LocalDate toDate) {
		super();
		this.employeeId = employeeId;
		this.itemId = itemId;
		this.fromDate = fromDate;
		this.toDate = toDate;
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

	public LocalDate getFromDate() {
		return fromDate;
	}

	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	public LocalDate getToDate() {
		return toDate;
	}

	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}
	
	
	

}
