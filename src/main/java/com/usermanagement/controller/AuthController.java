package com.usermanagement.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usermanagement.entity.Employee;
import com.usermanagement.entity.User;
import com.usermanagement.requestDto.LoginRequestDto;
import com.usermanagement.responseDto.EmployeeResponseDto;
import com.usermanagement.responseDto.LoginResponseDto;
import com.usermanagement.responseDto.UserDto;
import com.usermanagement.security.CustomUserDetails;
import com.usermanagement.security.JwtTokenProvider;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "APIs related to user authentication and JWT token generation")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@PostMapping("/login")
	@Operation(summary = "User Login", description = "Authenticates user credentials and returns JWT token along with user details, roles, privileges, and employee information")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getName(), loginRequestDto.getPassword())
        );

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        // generate token with authorities included
        String token = jwtTokenProvider.generateToken(customUserDetails);

        // Build roles and privileges lists
        List<String> roles = customUserDetails.getUser().getRoles().stream()
                .map(role -> "ROLE_" + role.getName().toUpperCase())
                .collect(Collectors.toList());

        // If you want distinct privileges (flatten roles -> privileges):
        List<String> privileges = customUserDetails.getUser().getRoles().stream()
                .filter(r -> r.getPrivileges() != null)
                .flatMap(r -> r.getPrivileges().stream())
                .map(p -> p.getName())
                .distinct()
                .collect(Collectors.toList());

        // Build user DTO (avoid including password)
        User userEntity = customUserDetails.getUser(); 
        UserDto userDto = new UserDto();
        userDto.setId(userEntity.getId());           
        userDto.setUsername(userEntity.getUsername());
        userDto.setEmail(userEntity.getEmail());      
        userDto.setRoles(roles);
        userDto.setPrivileges(privileges);
        
       Employee em= userEntity.getEmployee();
      
      userDto.setEmployeeResponseDto(new EmployeeResponseDto(em.getId(),em.getFirstName(),em.getLastName(),em.getPhone(),em.getDepartment()
    		  ,em.getDesignation(),em.getJoiningDate()));
       

        LoginResponseDto loginResponse = new LoginResponseDto(token, userDto);
        return ResponseEntity.ok(loginResponse);
    }

}
