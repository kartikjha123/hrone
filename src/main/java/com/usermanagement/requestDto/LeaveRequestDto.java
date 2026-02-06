package com.usermanagement.requestDto;

import java.time.LocalDate;

public class LeaveRequestDto {

	private Long employeId;
	private Long leaveTypeId;
	private LocalDate fromDate;
	private LocalDate toDate;
	private String reason;

	public LeaveRequestDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LeaveRequestDto(Long employeId, Long leaveTypeId, LocalDate fromDate, LocalDate toDate, String reason) {
		super();
		this.employeId = employeId;
		this.leaveTypeId = leaveTypeId;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.reason = reason;
	}

	public Long getEmployeId() {
		return employeId;
	}

	public void setEmployeId(Long employeId) {
		this.employeId = employeId;
	}

	public Long getLeaveTypeId() {
		return leaveTypeId;
	}

	public void setLeaveTypeId(Long leaveTypeId) {
		this.leaveTypeId = leaveTypeId;
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

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
