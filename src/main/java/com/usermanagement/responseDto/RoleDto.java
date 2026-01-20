package com.usermanagement.responseDto;

import java.util.HashSet;
import java.util.Set;

public class RoleDto {

	private Long id;
	private String name;
	private Set<PrivilegeDTO> privileges = new HashSet<>();
	
	public RoleDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RoleDto(Long id, String name, Set<PrivilegeDTO> privileges) {
		super();
		this.id = id;
		this.name = name;
		this.privileges = privileges;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<PrivilegeDTO> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(Set<PrivilegeDTO> privileges) {
		this.privileges = privileges;
	}
	

	
}
