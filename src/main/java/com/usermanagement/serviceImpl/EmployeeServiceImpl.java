package com.usermanagement.serviceImpl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usermanagement.entity.Employee;
import com.usermanagement.repository.EmployeeRepository;
import com.usermanagement.service.EmployeeService;
import com.usermanagement.service.LeaveBalanceService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    LeaveBalanceService leaLeaveBalanceService;

    @Override
    public Employee createEmployee(Employee employee) {
    	
    	leaLeaveBalanceService.createLeaveBalance(employee, getCurrentFinancialYear());
        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployee(Long id, Employee employee) {
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        existing.setFirstName(employee.getFirstName());
        existing.setLastName(employee.getLastName());
        existing.setPhone(employee.getPhone());
        existing.setDepartment(employee.getDepartment());
        existing.setDesignation(employee.getDesignation());
        return employeeRepository.save(existing);
    }

    @Override
    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new RuntimeException("Employee not found");
        }
        employeeRepository.deleteById(id);
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public void assignManager(Long employeeId, Long managerId) {
        Employee emp = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        Employee manager = employeeRepository.findById(managerId)
                .orElseThrow(() -> new RuntimeException("Manager not found"));
        emp.setManager(manager);
        employeeRepository.save(emp);
    }
    
    public int getCurrentFinancialYear() {
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        int month = today.getMonthValue();

        return (month >= 4) ? year : year - 1;
    }
}
