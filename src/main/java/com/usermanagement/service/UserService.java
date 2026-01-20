package com.usermanagement.service;

import java.util.List;

import com.usermanagement.entity.Privilege;
import com.usermanagement.entity.Role;
import com.usermanagement.entity.User;
import com.usermanagement.requestDto.AssignPrivilegesRequest;
import com.usermanagement.requestDto.AssignRolesRequest;
import com.usermanagement.requestDto.PrivilegeRequestDto;
import com.usermanagement.requestDto.RoleRequestDto;
import com.usermanagement.requestDto.UserRequestDto;
import com.usermanagement.responseDto.RoleDto;
import com.usermanagement.responseDto.UserResponseDto;

import jakarta.validation.Valid;

public interface UserService {
	
	public UserResponseDto createUser(UserRequestDto userRequestDto);

	public RoleDto createRole(@Valid RoleRequestDto roleRequestDto);

	public  Privilege createPrivilege(@Valid PrivilegeRequestDto privilegeRequestDto);

	public UserResponseDto assignUserWithRole(AssignRolesRequest assignRolesRequest);
	
	public Role assignPrivilegesToRole(AssignPrivilegesRequest assignPrivilegesRequest);

	public List<UserResponseDto> getAllUser(); 
	
	public List<RoleDto> getAllRoleWithPrivilage();

	public List<RoleDto> getAllRole();
}
