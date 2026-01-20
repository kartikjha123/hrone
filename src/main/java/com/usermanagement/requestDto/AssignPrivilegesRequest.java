package com.usermanagement.requestDto;

import java.util.List;

public class AssignPrivilegesRequest {

	private Long roleId;
	private List<Long> privilegeIds;

	// Getters and Setters
	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public List<Long> getPrivilegeIds() {
		return privilegeIds;
	}

	public void setPrivilegeIds(List<Long> privilegeIds) {
		this.privilegeIds = privilegeIds;
	}
}
