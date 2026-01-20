package com.usermanagement.responseDto;

import java.util.List;

public class UserResponseDto {

	private Long id;
	private String username;
	private String email;
	private boolean enabled;
	private List<RoleDto> roles;
private EmployeeResponseDto employeeResponseDto;

	public UserResponseDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public UserResponseDto(Long id, String username, String email, boolean enabled, List<RoleDto> roles) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.enabled = enabled;
		this.roles = roles;
	}



	public UserResponseDto(Long id2, String username2, String email2, boolean enabled2, List<RoleDto> roleDtos,
			EmployeeResponseDto empDto) {
		// TODO Auto-generated constructor stub
	}



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

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public List<RoleDto> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleDto> roles) {
		this.roles = roles;
	}



	public EmployeeResponseDto getEmployeeResponseDto() {
		return employeeResponseDto;
	}



	public void setEmployeeResponseDto(EmployeeResponseDto employeeResponseDto) {
		this.employeeResponseDto = employeeResponseDto;
	}
	
	

}
