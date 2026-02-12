package com.usermanagement.service;

import com.usermanagement.entity.Payroll;
import java.util.List;

public interface PayrollService {
    Payroll processPayroll(Long employeeId, int month, int year);
    List<Payroll> getMonthlyPayrollReport(int month, int year);
    void updatePayrollStatus(Long payrollId, String status);
}
