package com.usermanagement.serviceImpl;



import com.usermanagement.entity.Employee;
import com.usermanagement.repository.EmployeeRepository;
import com.usermanagement.responseDto.BirthdayResponseDto;
import com.usermanagement.service.BirthdayService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BirthdayServiceImpl implements BirthdayService {

    private final EmployeeRepository employeeRepository;

    public BirthdayServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<BirthdayResponseDto> getTodayBirthdays() {
        LocalDate today = LocalDate.now();
        return employeeRepository
                .findTodayBirthdays(today.getMonthValue(), today.getDayOfMonth())
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BirthdayResponseDto> getUpcomingBirthdaysThisMonth() {
        LocalDate today = LocalDate.now();
        return employeeRepository
                .findUpcomingBirthdaysThisMonth(
                        today.getMonthValue(), today.getDayOfMonth())
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // ✅ Entity → DTO mapper
    private BirthdayResponseDto toDto(Employee emp) {
        BirthdayResponseDto dto = new BirthdayResponseDto();
        dto.setEmployeeId(emp.getId());
        dto.setEmployeeCode(emp.getEmployeeCode());
        dto.setEmployeeName(emp.getFirstName() + " " + emp.getLastName());
        dto.setDepartment(emp.getDepartment());
        dto.setDesignation(emp.getDesignation());
        dto.setPhone(emp.getPhone());

        if (emp.getDateOfBirth() != null) {
            dto.setDateOfBirth(emp.getDateOfBirth().toString());
            // ✅ Age calculate — aaj kitne saal ke hue
            int age = Period.between(emp.getDateOfBirth(), LocalDate.now()).getYears();
            dto.setAge(age);
        }

        return dto;
    }
}