package com.usermanagement.serviceImpl;

import com.usermanagement.entity.*;
import com.usermanagement.repository.*;
import com.usermanagement.service.PayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
public class PayrollServiceImpl implements PayrollService {

    @Autowired
    private PayrollRepository payrollRepository;

    @Autowired
    private SalaryStructureRepository salaryStructureRepository;

    @Autowired
    private ProductionEntryRepository productionEntryRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private AdvanceSalaryRepository advanceSalaryRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Payroll processPayroll(Long employeeId, int month, int year) {
        Employee emp = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        SalaryStructure structure = salaryStructureRepository.findByEmployeeId(employeeId)
                .orElse(new SalaryStructure()); // Fallback to zero if not defined

        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        // 1. Production Earnings (Image 1 logic)
        Double prodEarnings = productionEntryRepository.sumApprovedAmountByEmployeeAndDateRange(employeeId, startDate, endDate);
        if (prodEarnings == null) prodEarnings = 0.0;

        // 2. OT Earnings (Image 2 logic)
        List<Attendance> attendanceRecords = attendanceRepository.findByEmployeeIdAndDateBetween(employeeId, startDate, endDate);
        Double totalOTHours = attendanceRecords.stream()
                .mapToDouble(a -> a.getOvertimeHours() != null ? a.getOvertimeHours() : 0.0)
                .sum();
        Double otRate = 100.0; // Assume 100 per hour for now, can be part of salary structure
        Double otEarnings = totalOTHours * otRate;

        // 3. Advance Deductions (Image 2 logic)
        List<AdvanceSalary> advances = advanceSalaryRepository.findByEmployeeId(employeeId).stream()
                .filter(a -> "APPROVED".equals(a.getStatus()) && a.getPaymentDate() != null && 
                             a.getPaymentDate().getMonthValue() == month && a.getPaymentDate().getYear() == year)
                .toList();
        Double totalAdvance = advances.stream().mapToDouble(AdvanceSalary::getAmount).sum();

        // 4. Base Salary
        Double baseSalary = (structure.getBasic() != null ? structure.getBasic() : 0.0) +
                            (structure.getHra() != null ? structure.getHra() : 0.0) +
                            (structure.getDa() != null ? structure.getDa() : 0.0);

        // 5. Statutory Deductions (Placeholder Logic)
        Double pfDeduction = baseSalary * 0.12; // 12% PF
        Double esiDeduction = baseSalary < 21000 ? baseSalary * 0.0075 : 0.0; // 0.75% ESI if salary < 21k
        Double tdsDeduction = baseSalary > 50000 ? baseSalary * 0.10 : 0.0; // 10% TDS if base > 50k

        // 6. Net Salary
        Double netSalary = baseSalary + prodEarnings + otEarnings - totalAdvance - pfDeduction - esiDeduction - tdsDeduction;

        Payroll payroll = payrollRepository.findByEmployeeIdAndMonthAndYear(employeeId, month, year)
                .orElse(new Payroll());
        
        payroll.setEmployee(emp);
        payroll.setMonth(month);
        payroll.setYear(year);
        payroll.setBaseSalary(baseSalary);
        payroll.setProductionEarnings(prodEarnings);
        payroll.setOtEarnings(otEarnings);
        payroll.setAdvanceDeduction(totalAdvance);
        payroll.setNetSalary(netSalary);
        payroll.setProcessedDate(LocalDate.now());
        payroll.setStatus("DRAFT");

        return payrollRepository.save(payroll);
    }

    @Override
    public List<Payroll> getMonthlyPayrollReport(int month, int year) {
        return payrollRepository.findByMonthAndYear(month, year);
    }

    @Override
    public void updatePayrollStatus(Long payrollId, String status) {
        Payroll payroll = payrollRepository.findById(payrollId)
                .orElseThrow(() -> new RuntimeException("Payroll record not found"));
        payroll.setStatus(status);
        payrollRepository.save(payroll);
    }
}
