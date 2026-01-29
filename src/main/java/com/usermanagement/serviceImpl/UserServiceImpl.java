package com.usermanagement.serviceImpl;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.usermanagement.entity.Employee;
import com.usermanagement.entity.ItemMaster;
import com.usermanagement.entity.Privilege;
import com.usermanagement.entity.ProductionEntry;
import com.usermanagement.entity.Role;
import com.usermanagement.entity.User;
import com.usermanagement.repository.EmployeeRepository;
import com.usermanagement.repository.ItemMasterRepository;
import com.usermanagement.repository.PrivilegeRepository;
import com.usermanagement.repository.ProductionEntryRepository;
import com.usermanagement.repository.RoleRepository;
import com.usermanagement.repository.UserRepository;
import com.usermanagement.requestDto.AssignPrivilegesRequest;
import com.usermanagement.requestDto.AssignRolesRequest;
import com.usermanagement.requestDto.EmployeeRequestDto;
import com.usermanagement.requestDto.ItemMasterRequestDto;
import com.usermanagement.requestDto.PrivilegeRequestDto;
import com.usermanagement.requestDto.ProductionEntryRequestDto;
import com.usermanagement.requestDto.ProductionFilterRequestDto;
import com.usermanagement.requestDto.RoleRequestDto;
import com.usermanagement.requestDto.UserRequestDto;
import com.usermanagement.responseDto.EmployeeResponseDto;
import com.usermanagement.responseDto.PrivilegeDTO;
import com.usermanagement.responseDto.ProductionEntryResponseDto;
import com.usermanagement.responseDto.RoleDto;
import com.usermanagement.responseDto.UserResponseDto;
import com.usermanagement.service.UserService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private PrivilegeRepository privilegeRepository;
	
	@Autowired
	private ItemMasterRepository itemMasterRepository;
	
	@Autowired
	private ProductionEntryRepository productionEntryRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public UserResponseDto createUser(UserRequestDto userRequestDto) {

		// check existing username
		if (userRepository.findByUsername(userRequestDto.getEmail()).isPresent()) {
			throw new IllegalArgumentException("Username already exists: " + userRequestDto.getUsername());
		}

		// create user and hash password
		User user = new User();
		user.setUsername(userRequestDto.getUsername());
		// user.setPassword(passwordEncoder.encode(rawPassword));
		user.setEmail(userRequestDto.getEmail());
		user.setEnabled(true);
		user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));

		// resolve roles by name (if provided), otherwise assign default ROLE_USER if
		// exists
		Set<Role> roles = new HashSet<>();
		if (userRequestDto.getRoles() != null && !userRequestDto.getRoles().isEmpty()) {
			for (String rn : userRequestDto.getRoles()) {
				Role r = roleRepository.findByName(rn)
						.orElseThrow(() -> new NoSuchElementException("Role not found: " + rn));
				roles.add(r);
			}
		} else {
			roleRepository.findByName("USER").ifPresent(roles::add);
		}
		user.setRoles(roles);

		// map and attach employee if provided
		if (userRequestDto.getEmployee() != null) {
			EmployeeRequestDto er = userRequestDto.getEmployee();
			Employee emp = new Employee();
			emp.setFirstName(er.getFirstName());
			emp.setLastName(er.getLastName());
			emp.setPhone(er.getPhone());
			emp.setDepartment(er.getDepartment());
			emp.setDesignation(er.getDesignation());
			emp.setJoiningDate(er.getJoiningDate());
			emp.setEmployeeCode(er.getEmployeeCode());
			// set bidirectional association
			emp.setUser(user);
			user.setEmployee(emp); // if you added employee field to User (see note)
		}

		userRepository.save(user);

		return mapToUserResponseDto(user);
	}

	@Override
	public RoleDto createRole(@Valid RoleRequestDto roleRequestDto) {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		System.out.println("   ------------- " + username);

		if (roleRepository.findByName(roleRequestDto.getName()).isPresent()) {
			throw new IllegalArgumentException("Username already exists: " + roleRequestDto.getName());
		}
		Role role = new Role();
		role.setName(roleRequestDto.getName());
		role.setPrivileges(null);
		roleRepository.save(role);
		RoleDto roleDto = new RoleDto();
		roleDto.setId(role.getId());
		roleDto.setName(role.getName());
		return roleDto;
	}

	@Override
	public Privilege createPrivilege(@Valid PrivilegeRequestDto privilegeRequestDto) {
		// TODO Auto-generated method stub
		Privilege privilege = new Privilege();
		privilege.setName(privilegeRequestDto.getName());
		privilegeRepository.save(privilege);
		return privilege;
	}

	@Override
	@Transactional
	public UserResponseDto assignUserWithRole(AssignRolesRequest req) {
		// 1) load user
		User user = userRepository.findById(req.getUserId())
				.orElseThrow(() -> new NoSuchElementException("User not found: " + req.getUserId()));

		// 2) load roles by ids
		List<Long> roleIds = Optional.ofNullable(req.getRoleId()).orElse(Collections.emptyList());

		if (roleIds.isEmpty()) {
			// If UI sends empty list and you want to clear roles:
			user.setRoles(new HashSet<>());
			User sa = userRepository.save(user);
			return mapToUserResponseDto(sa);
		}

		List<Role> roles = roleRepository.findAllById(roleIds);

		// 3) ensure all requested ids exist
		Set<Long> foundIds = roles.stream().map(Role::getId).collect(Collectors.toSet());
		List<Long> missing = roleIds.stream().filter(id -> !foundIds.contains(id)).collect(Collectors.toList());

		if (!missing.isEmpty()) {
			throw new NoSuchElementException("Roles not found for ids: " + missing);
		}

		user.setRoles(new HashSet<>(roles));

		User saved = userRepository.save(user);

		// 5) return mapped DTO
		return mapToUserResponseDto(saved);
	}

	@Override
	@Transactional
	public Role assignPrivilegesToRole(AssignPrivilegesRequest request) {
		// 1) load role
		Role role = roleRepository.findById(request.getRoleId())
				.orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + request.getRoleId()));

		// 2) load privilegeIds defensively, dedupe and handle null
		List<Long> privilegeIds = Optional.ofNullable(request.getPrivilegeIds()).orElse(Collections.emptyList())
				.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());

		// 3) if UI sends empty list -> clear privileges
		if (privilegeIds.isEmpty()) {
			role.setPrivileges(new HashSet<>()); // prefer empty set over null
			return roleRepository.save(role);
		}

		// 4) fetch privileges by ids
		List<Privilege> privileges = privilegeRepository.findAllById(privilegeIds);

		// 5) ensure all requested ids exist
		Set<Long> foundIds = privileges.stream().map(Privilege::getId).collect(Collectors.toSet());

		List<Long> missing = privilegeIds.stream().filter(id -> !foundIds.contains(id)).collect(Collectors.toList());

		if (!missing.isEmpty()) {
			throw new EntityNotFoundException("Privileges not found for ids: " + missing);
		}

		// 6) replace privileges
		role.setPrivileges(new HashSet<>(privileges));

		// 7) save and return
		return roleRepository.save(role);
	}

	public UserResponseDto getCurrentUserProfile() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null || !auth.isAuthenticated()) {
			throw new AccessDeniedException("User is not authenticated");
		}

		String username;
		Object principal = auth.getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else if (principal instanceof String) {
			username = (String) principal;
		} else {
			throw new UsernameNotFoundException("Cannot extract username from principal");
		}

		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

		return mapToUserResponseDto(user);
	}

	private UserResponseDto mapToUserResponseDto(User u) {
		if (u == null)
			return null;
		UserResponseDto dto = new UserResponseDto();
		dto.setId(u.getId());
		dto.setUsername(u.getUsername());
		dto.setEmail(u.getEmail());
		dto.setEnabled(u.isEnabled());

		// map roles (Set<Role> -> List<RoleDto>)
		if (u.getRoles() != null) {
			List<RoleDto> roleDtos = u.getRoles().stream().map(this::mapToRoleDto).collect(Collectors.toList());
			dto.setRoles(roleDtos);
		} else {
			dto.setRoles(Collections.emptyList());
		}

		// map employee (if present)
		Employee emp = u.getEmployee();
		if (emp != null) {
			dto.setEmployeeResponseDto(mapToEmployeeResponseDto(emp));
		} else {
			dto.setEmployeeResponseDto(null);
		}

		return dto;
	}

	private RoleDto mapToRoleDto(Role r) {
		if (r == null)
			return null;
		RoleDto dto = new RoleDto();
		dto.setId(r.getId());
		dto.setName(r.getName());

		if (r.getPrivileges() != null) {
			Set<PrivilegeDTO> privDtos = r.getPrivileges().stream().map(this::mapToPrivilegeDto)
					.collect(Collectors.toSet());
			dto.setPrivileges(privDtos);
		} else {
			dto.setPrivileges(Collections.emptySet());
		}

		return dto;
	}

	private PrivilegeDTO mapToPrivilegeDto(Privilege p) {
		if (p == null)
			return null;
		PrivilegeDTO dto = new PrivilegeDTO();
		dto.setId(p.getId());
		dto.setName(p.getName());
		return dto;
	}

	private EmployeeResponseDto mapToEmployeeResponseDto(Employee e) {
		if (e == null)
			return null;
		EmployeeResponseDto dto = new EmployeeResponseDto();
		dto.setId(e.getId());
		dto.setFirstName(e.getFirstName());
		dto.setLastName(e.getLastName());
		dto.setPhone(e.getPhone());
		dto.setDepartment(e.getDepartment());
		dto.setDesignation(e.getDesignation());
		// assuming joiningDate is LocalDate or String - adapt if different
		dto.setJoiningDate(e.getJoiningDate());
		return dto;
	}

	@Override
	public List<UserResponseDto> getAllUser() {
		// TODO Auto-generated method stub
		List<User> users = userRepository.findAll();
		
		if (users == null || users.isEmpty()) {
			return Collections.emptyList();
		}

		return users.stream().map(this::mapToUserResponseDto).collect(Collectors.toList());

	}

	@Override
	public List<RoleDto> getAllRoleWithPrivilage() {
		// TODO Auto-generated method stub

		List<Role> roles = roleRepository.findAll();

		return roles.stream().map(this::mapToRoleDto).collect(Collectors.toList());
	}

	@Override
	public List<RoleDto> getAllRole() {
		// TODO Auto-generated method stub
		List<Role> roles = roleRepository.findAll();
		return roles.stream().map(r -> new RoleDto(r.getId(), r.getName(), null)).collect(Collectors.toList());
	}

	@Override
	public List<PrivilegeDTO> getAllPrivilage() {
		// TODO Auto-generated method stub
		List<Privilege> privileges = privilegeRepository.findAll();
		return privileges.stream().map(p -> new PrivilegeDTO(p.getId(), p.getName())).collect(Collectors.toList());
	}

	@Override
	public void addItemMaster(ItemMasterRequestDto itemMasterRequestDto) {
		// TODO Auto-generated method stub
		ItemMaster itemMaster = new ItemMaster();
		itemMaster.setItemName(itemMasterRequestDto.getItemName());
		itemMaster.setRate(itemMasterRequestDto.getRate());
		itemMaster.setUnit(itemMasterRequestDto.getUnit());
		//itemMaster.setCreatedBy(getCurrentUserProfile().getUsername());
		itemMaster.setCreatedDate(LocalDate.now()); 
		
		itemMasterRepository.save(itemMaster);
		
		

	}

	
		
		
		
		
	
}
