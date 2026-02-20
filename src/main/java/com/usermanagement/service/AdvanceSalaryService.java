package com.usermanagement.service;

import com.usermanagement.entity.AdvanceSalary;
import com.usermanagement.requestDto.AdvanceSalaryRequestDto;
import java.util.List;

public interface AdvanceSalaryService {
    void requestAdvance(AdvanceSalaryRequestDto request);
    void approveAdvance(Long advanceId, Long adminId, String status, String comments);
    List<AdvanceSalary> getEmployeeAdvances(Long employeeId);
    List<AdvanceSalary> getAllPendingAdvances();

    void updateAdvance(Long id, AdvanceSalaryRequestDto request);

    void deleteAdvance(Long id);
}
