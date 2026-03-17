package com.usermanagement.responseDto;

public class ManagerAttendanceResponseDto {
	private Long attendanceId;
	private Long employeeId;
	private String employeeCode;
	private String employeeName; // firstName + lastName
	private String department;
	private String designation;
	private String date;
	private String status;
	private String punchIn;
	private String punchOut;
	private String totalHours;
	private String approvalStatus;
	
	public ManagerAttendanceResponseDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ManagerAttendanceResponseDto(Long attendanceId, Long employeeId, String employeeCode, String employeeName,
			String department, String designation, String date, String status, String punchIn, String punchOut,
			String totalHours, String approvalStatus) {
		super();
		this.attendanceId = attendanceId;
		this.employeeId = employeeId;
		this.employeeCode = employeeCode;
		this.employeeName = employeeName;
		this.department = department;
		this.designation = designation;
		this.date = date;
		this.status = status;
		this.punchIn = punchIn;
		this.punchOut = punchOut;
		this.totalHours = totalHours;
		this.approvalStatus = approvalStatus;
	}

	public Long getAttendanceId() {
		return attendanceId;
	}

	public void setAttendanceId(Long attendanceId) {
		this.attendanceId = attendanceId;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPunchIn() {
		return punchIn;
	}

	public void setPunchIn(String punchIn) {
		this.punchIn = punchIn;
	}

	public String getPunchOut() {
		return punchOut;
	}

	public void setPunchOut(String punchOut) {
		this.punchOut = punchOut;
	}

	public String getTotalHours() {
		return totalHours;
	}

	public void setTotalHours(String totalHours) {
		this.totalHours = totalHours;
	}

	public String getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	
	
	
}