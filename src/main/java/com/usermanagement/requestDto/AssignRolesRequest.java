package com.usermanagement.requestDto;

import java.util.List;

public class AssignRolesRequest {
	
	private Long userId;
	private List<Long> roleId;
	
	
	
	public AssignRolesRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public AssignRolesRequest(Long userId, List<Long> roleId) {
		super();
		this.userId = userId;
		this.roleId = roleId;
	}


	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public List<Long> getRoleId() {
		return roleId;
	}
	public void setRoleId(List<Long> roleId) {
		this.roleId = roleId;
	}
	
	
	

}
