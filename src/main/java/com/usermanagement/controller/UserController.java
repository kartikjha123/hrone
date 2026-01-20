package com.usermanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usermanagement.requestDto.AssignPrivilegesRequest;
import com.usermanagement.requestDto.AssignRolesRequest;
import com.usermanagement.requestDto.PrivilegeRequestDto;
import com.usermanagement.requestDto.RoleRequestDto;
import com.usermanagement.requestDto.UserRequestDto;
import com.usermanagement.responseDto.ResponseMessageDto;
import com.usermanagement.responseDto.RoleDto;
import com.usermanagement.responseDto.UserResponseDto;
import com.usermanagement.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping("/create")
	public ResponseEntity<ResponseMessageDto> createUser(@Valid @RequestBody UserRequestDto userRequestDto )
	{
		
		return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "User Created Successfully", userService.createUser(userRequestDto)));
		
	}
	
	
	@PostMapping("/create-role")
	public ResponseEntity<?> createRole(@Valid @RequestBody RoleRequestDto roleRequestDto)
	{
		return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "Role Created Successfully", userService.createRole(roleRequestDto)));

	}
	
	@PostMapping("/create-privilege")
	public ResponseEntity<?> createPrivilege(@RequestBody PrivilegeRequestDto privilegeRequestDto)
	{
		return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "Privilege Created Successfully", userService.createPrivilege(privilegeRequestDto)));

	}
	
	@PostMapping("/assign-role")
	public ResponseEntity<?> assignUserWithRole(@RequestBody AssignRolesRequest assignRolesRequest)
	{
		return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "AssignUserWithRole Successfully", userService.assignUserWithRole(assignRolesRequest)));

	}
	
	@PostMapping("/assign-privilage")
	public ResponseEntity<?> assignRoleWithPrivillage(@RequestBody AssignPrivilegesRequest assignPrivilegesRequest )
	{
		return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "AssignRoleWithPrivillage Successfully", userService.assignPrivilegesToRole(assignPrivilegesRequest)));

	}
	
	@PostMapping("/all")
	public ResponseEntity<ResponseMessageDto> getAllUser() {

	    List<UserResponseDto> list = userService.getAllUser();

		String message = list.isEmpty() ? "No Data Found" : "User list extracted successfully";

		return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), message, list));
	}

	
	@PostMapping("/all-role-privilage")
	public ResponseEntity<ResponseMessageDto> getAllRoleWithPrivilage() {

		List<RoleDto> roles = userService.getAllRoleWithPrivilage();

		String message = roles.isEmpty() ? "No Data Found" : "AllRoleWithPrivilage list fetch successfully";

		return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), message, roles));

	}
	
	@PostMapping("/all-role")
	public ResponseEntity<?> getAllRole() {
		List<RoleDto> roles = userService.getAllRole();
		String message = roles.isEmpty() ? "No Data Found" : "All Roles list fetch successfully";
		return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(),message ,roles));
	}

}
