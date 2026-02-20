package com.usermanagement.service;

import com.usermanagement.entity.SalaryStructure;
import java.util.Optional;

public interface SalaryStructureService {
    SalaryStructure saveOrUpdate(Long employeeId, SalaryStructure structure);
    Optional<SalaryStructure> getByEmployeeId(Long employeeId);
    void deleteByEmployeeId(Long employeeId);
}
