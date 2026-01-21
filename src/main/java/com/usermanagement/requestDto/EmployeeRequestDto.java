package com.usermanagement.requestDto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class EmployeeRequestDto {

	@NotBlank(message = "First name is required")
	@Size(min = 2, max = 50, message = "First name should be 2 to 50 characters long")
	private String firstName;

	@NotBlank(message = "Last name is required")
	@Size(min = 2, max = 50, message = "Last name should be 2 to 50 characters long")
	private String lastName;

	@Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be a valid 10-digit number")
	private String phone;

	@Size(max = 100, message = "Department cannot exceed 100 characters")
	private String department;

	@Size(max = 100, message = "Designation cannot exceed 100 characters")
	private String designation;

	private LocalDate joiningDate;
	
	private String employeeCode;

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

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	

}
