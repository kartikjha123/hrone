package com.usermanagement.service;

import com.usermanagement.entity.Payroll;
import com.usermanagement.responseDto.PayrollResponseDto;

import java.util.List;

public interface PayrollService {
    Payroll processPayroll(Long employeeId, int month, int year);
    List<PayrollResponseDto> getMonthlyPayrollReport(int month, int year);
    void updatePayrollStatus(Long payrollId, String status);

    void deletePayroll(Long id);
}
