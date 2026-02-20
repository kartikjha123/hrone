package com.usermanagement.serviceImpl;

import com.usermanagement.entity.Employee;
import com.usermanagement.entity.SalaryStructure;
import com.usermanagement.repository.EmployeeRepository;
import com.usermanagement.repository.SalaryStructureRepository;
import com.usermanagement.service.SalaryStructureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

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
    public Optional<SalaryStructure> getByEmployeeId(Long employeeId) {
        return salaryStructureRepository.findByEmployeeId(employeeId);
    }

    @Override
    public void deleteByEmployeeId(Long employeeId) {
        SalaryStructure existing = salaryStructureRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new RuntimeException("Salary structure not found"));
        salaryStructureRepository.delete(existing);
    }
}
