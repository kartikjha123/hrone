package com.usermanagement.requestDto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserRequestDto {
	@NotBlank(message = "Username is required")
	@Size(min = 3, max = 100, message = "Username must be between 3 and 100 characters")
	@Pattern(regexp = "^[A-Za-z0-9._-]+$", message = "Username can only contain letters, numbers, dots, underscores, and hyphens")
	private String username;

	@NotBlank(message = "Password is required")
	@Size(min = 6, max = 128, message = "Password must be between 6 and 128 characters")
	private String password;

	@NotBlank(message = "Email is required")
	@Email(message = "Email should be valid")
	private String email;

	private List<String> roles;

	@Valid
	private EmployeeRequestDto employee; // optional
	// getters/setters

	/**
	 * Optional list of role names to assign to the user. If null or empty you can
	 * choose to assign a default role in service.
	 */

	// getters / setters
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public EmployeeRequestDto getEmployee() {
		return employee;
	}

	public void setEmployee(EmployeeRequestDto employee) {
		this.employee = employee;
	}

}
