package com.usermanagement.responseDto;
//com.usermanagement.dto.UserDto

import java.util.List;



public class UserDto {
	private Long id;
	private String username;
	private String email;
	private List<String> roles; // e.g. ROLE_ADMIN
	private List<String> privileges; // e.g. READ_PRIVILEGE
	private EmployeeResponseDto employeeResponseDto;
	

	// constructors
	public UserDto() {
	}

	public UserDto(Long id, String username, String email, List<String> roles, List<String> privileges) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.roles = roles;
		this.privileges = privileges;
	}

	// getters & setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public List<String> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(List<String> privileges) {
		this.privileges = privileges;
	}

	public EmployeeResponseDto getEmployeeResponseDto() {
		return employeeResponseDto;
	}

	public void setEmployeeResponseDto(EmployeeResponseDto employeeResponseDto) {
		this.employeeResponseDto = employeeResponseDto;
	}

	

	
	
	
}
