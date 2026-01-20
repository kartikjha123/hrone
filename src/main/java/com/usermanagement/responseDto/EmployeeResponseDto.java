package com.usermanagement.responseDto;

import java.time.LocalDate;

public class EmployeeResponseDto {

	private Long id;
	private String firstName;
	private String lastName;
	private String phone;
	private String department;
	private String designation;
	private LocalDate joiningDate;
	
	
	
	public EmployeeResponseDto(Long id, String firstName, String lastName, String phone, String department,
			String designation, LocalDate joiningDate) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.department = department;
		this.designation = designation;
		this.joiningDate = joiningDate;
	}
	public EmployeeResponseDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
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
	public LocalDate getJoiningDate() {
		return joiningDate;
	}
	public void setJoiningDate(LocalDate joiningDate) {
		this.joiningDate = joiningDate;
	}
	
	

}
