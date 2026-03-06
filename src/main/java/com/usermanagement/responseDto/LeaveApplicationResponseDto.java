package com.usermanagement.responseDto;

import java.time.LocalDate;

public class LeaveApplicationResponseDto {

	private Long id;
	private String employeeName;
	private String leaveType;
	private LocalDate fromDate;
	private LocalDate toDate;
	private int totalDays;
	private String reason;
	private String status;
	private String managerComment;

	public LeaveApplicationResponseDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LeaveApplicationResponseDto(Long id, String employeeName, String leaveType, LocalDate fromDate,
			LocalDate toDate, int totalDays, String reason, String status, String managerComment) {
		super();
		this.id = id;
		this.employeeName = employeeName;
		this.leaveType = leaveType;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.totalDays = totalDays;
		this.reason = reason;
		this.status = status;
		this.managerComment = managerComment;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
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

	public int getTotalDays() {
		return totalDays;
	}

	public void setTotalDays(int totalDays) {
		this.totalDays = totalDays;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getManagerComment() {
		return managerComment;
	}

	public void setManagerComment(String managerComment) {
		this.managerComment = managerComment;
	}

}
