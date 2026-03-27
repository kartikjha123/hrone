package com.usermanagement.service;

import com.usermanagement.entity.SalaryStructure;
import com.usermanagement.responseDto.SalaryStructureResponseDto;

import java.util.Optional;

public interface SalaryStructureService {
    SalaryStructure saveOrUpdate(Long employeeId, SalaryStructure structure);
    SalaryStructureResponseDto getByEmployeeId(Long employeeId);
    void deleteByEmployeeId(Long employeeId);
}
