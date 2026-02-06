package com.usermanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usermanagement.requestDto.AssignEmployeeToManagerRequestDto;
import com.usermanagement.requestDto.AssignPrivilegesRequest;
import com.usermanagement.requestDto.AssignRolesRequest;
import com.usermanagement.requestDto.PrivilegeRequestDto;
import com.usermanagement.requestDto.RoleRequestDto;
import com.usermanagement.requestDto.UserRequestDto;
import com.usermanagement.responseDto.EmployeeManagerMappingResponseDto;
import com.usermanagement.responseDto.PrivilegeDTO;
import com.usermanagement.responseDto.ResponseMessageDto;
import com.usermanagement.responseDto.RoleDto;
import com.usermanagement.responseDto.UserResponseDto;
import com.usermanagement.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "User Management", description = "APIs for User, Role and Privilege Management")
@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Operation(summary = "Create User", description = "Creates a new user in the system")
	@PostMapping("/create")
	public ResponseEntity<ResponseMessageDto> createUser(@Valid @RequestBody UserRequestDto userRequestDto )
	{
		
		return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "User Created Successfully", userService.createUser(userRequestDto)));
		
	}
	
	@Operation(summary = "Create Role", description = "Creates a new role")
	@PostMapping("/create-role")
	public ResponseEntity<?> createRole(@Valid @RequestBody RoleRequestDto roleRequestDto)
	{
		return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "Role Created Successfully", userService.createRole(roleRequestDto)));

	}
	
	@Operation(summary = "Create Privilege", description = "Creates a new privilege")
	@PostMapping("/create-privilege")
	public ResponseEntity<?> createPrivilege(@RequestBody PrivilegeRequestDto privilegeRequestDto)
	{
		return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "Privilege Created Successfully", userService.createPrivilege(privilegeRequestDto)));

	}
	
	@Operation(summary = "Assign Role to User", description = "Assigns a role to a user")
	@PostMapping("/assign-role")
	public ResponseEntity<?> assignUserWithRole(@RequestBody AssignRolesRequest assignRolesRequest)
	{
		return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "AssignUserWithRole Successfully", userService.assignUserWithRole(assignRolesRequest)));

	}
	
	@Operation(summary = "Assign Privileges to Role", description = "Assigns privileges to a role")
	@PostMapping("/assign-privilage")
	public ResponseEntity<?> assignRoleWithPrivillage(@RequestBody AssignPrivilegesRequest assignPrivilegesRequest )
	{
		return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "AssignRoleWithPrivillage Successfully", userService.assignPrivilegesToRole(assignPrivilegesRequest)));

	}
	
	@Operation(summary = "Get All Users", description = "Fetches list of all users")
	@PostMapping("/all")
	public ResponseEntity<ResponseMessageDto> getAllUser() {

	    List<UserResponseDto> list = userService.getAllUser();

		String message = list.isEmpty() ? "No Data Found" : "User list extracted successfully";

		return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), message, list));
	}

	@Operation(summary = "Get Roles with Privileges", description = "Fetches all roles along with their privileges")
	@PostMapping("/all-role-privilage")
	public ResponseEntity<ResponseMessageDto> getAllRoleWithPrivilage() {

		List<RoleDto> roles = userService.getAllRoleWithPrivilage();

		String message = roles.isEmpty() ? "No Data Found" : "AllRoleWithPrivilage list fetch successfully";

		return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), message, roles));

	}
	
	@Operation(summary = "Get All Roles", description = "Fetches all roles")
	@PostMapping("/all-role")
	public ResponseEntity<?> getAllRole() {
		List<RoleDto> roles = userService.getAllRole();
		String message = roles.isEmpty() ? "No Data Found" : "All Roles list fetch successfully";
		return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(),message ,roles));
	}
	
	@Operation(summary = "Get All Privileges", description = "Fetches all privileges")
	@PostMapping("/all-privilage")
	public ResponseEntity<?> getAllPrivilage() {
		List<PrivilegeDTO> privilages = userService.getAllPrivilage();
		String message = privilages.isEmpty() ? "No Data Found" : "All Privilage list fetch successfully";
		return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), message, privilages));
	}
	
	
	
	@Operation(summary = "Assign Employee to Manager", description = "Maps an employee under a manager")
	@PostMapping("/assign-employee-manager")
	public ResponseEntity<ResponseMessageDto> assignEmployeeToManager(
			@RequestBody AssignEmployeeToManagerRequestDto dto) {

		userService.assignEmployeeToManager(dto);

		return ResponseEntity
				.ok(new ResponseMessageDto(HttpStatus.OK.value(), "Employee assigned to manager successfully"));
	}	
	
	
	@Operation(summary = "Get Employee Manager Mapping", description = "Shows which employee reports to which manager")
	@PostMapping("/employee-manager-mapping")
	public ResponseEntity<ResponseMessageDto> getEmployeeManagerMapping() {

		List<EmployeeManagerMappingResponseDto> list = userService.getEmployeeManagerMapping();

		String message = list.isEmpty() ? "No Mapping Found" : "Employee Manager Mapping fetched successfully";

		return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), message, list));
	}
	

}
