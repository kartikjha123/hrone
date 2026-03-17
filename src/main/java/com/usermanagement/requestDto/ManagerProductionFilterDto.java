package com.usermanagement.requestDto;

import java.time.LocalDate;

public class ManagerProductionFilterDto {
    private Long managerId;
    private String status;       // PENDING / APPROVED / REJECTED / null = all
    private LocalDate fromDate;
    private LocalDate toDate;
    private Integer page;
    private Integer size;
    
    
	public ManagerProductionFilterDto() {
		super();
		// TODO Auto-generated constructor stub
	}


	public ManagerProductionFilterDto(Long managerId, String status, LocalDate fromDate, LocalDate toDate, Integer page,
			Integer size) {
		super();
		this.managerId = managerId;
		this.status = status;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.page = page;
		this.size = size;
	}


	public Long getManagerId() {
		return managerId;
	}


	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
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
