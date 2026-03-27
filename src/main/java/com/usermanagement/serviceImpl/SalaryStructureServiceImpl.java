package com.usermanagement.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usermanagement.entity.Employee;
import com.usermanagement.entity.SalaryStructure;
import com.usermanagement.repository.EmployeeRepository;
import com.usermanagement.repository.SalaryStructureRepository;
import com.usermanagement.responseDto.SalaryStructureResponseDto;
import com.usermanagement.service.SalaryStructureService;

@Service
public class SalaryStructureServiceImpl implements SalaryStructureService {

    @Autowired
    private SalaryStructureRepository salaryStructureRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public SalaryStructure saveOrUpdate(Long employeeId, SalaryStructure structure) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        
        SalaryStructure existing = salaryStructureRepository.findByEmployeeId(employeeId)
                .orElse(new SalaryStructure());
        
        existing.setEmployee(employee);
        existing.setBasic(structure.getBasic());
        existing.setHra(structure.getHra());
        existing.setDa(structure.getDa());
        existing.setOtherAllowances(structure.getOtherAllowances());
        existing.setPfDeduction(structure.getPfDeduction());
        existing.setEsiDeduction(structure.getEsiDeduction());
        
        return salaryStructureRepository.save(existing);
    }

    @Override
    public SalaryStructureResponseDto getByEmployeeId(Long employeeId) {
        SalaryStructure s = salaryStructureRepository
            .findByEmployeeId(employeeId)
            .orElseThrow(() -> new RuntimeException("Not found"));

        SalaryStructureResponseDto dto = new SalaryStructureResponseDto();
        dto.setId(s.getId());
        dto.setBasic(s.getBasic());
        dto.setHra(s.getHra());
        dto.setDa(s.getDa());
        dto.setOtherAllowances(s.getOtherAllowances());
        dto.setPfDeduction(s.getPfDeduction());
        dto.setEsiDeduction(s.getEsiDeduction());

        // Employee info - sirf basic fields
        dto.setEmployeeName(
            s.getEmployee().getFirstName() + " " + s.getEmployee().getLastName()
        );
        dto.setEmployeeCode(s.getEmployee().getEmployeeCode());
        dto.setDepartment(s.getEmployee().getDepartment());

        return dto;
    }

    @Override
    public void deleteByEmployeeId(Long employeeId) {
        SalaryStructure existing = salaryStructureRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new RuntimeException("Salary structure not found"));
        salaryStructureRepository.delete(existing);
    }
}
