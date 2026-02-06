package com.usermanagement.requestDto;

public class AssignEmployeeToManagerRequestDto {

	private Long employeeId;
	private Long managerId;

	public AssignEmployeeToManagerRequestDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AssignEmployeeToManagerRequestDto(Long employeeId, Long managerId) {
		super();
		this.employeeId = employeeId;
		this.managerId = managerId;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public Long getManagerId() {
		return managerId;
	}

	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}

}
