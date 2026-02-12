package com.usermanagement.serviceImpl;

import com.usermanagement.entity.AdvanceSalary;
import com.usermanagement.entity.Employee;
import com.usermanagement.repository.AdvanceSalaryRepository;
import com.usermanagement.repository.EmployeeRepository;
import com.usermanagement.requestDto.AdvanceSalaryRequestDto;
import com.usermanagement.service.AdvanceSalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class AdvanceSalaryServiceImpl implements AdvanceSalaryService {

    @Autowired
    private AdvanceSalaryRepository advanceSalaryRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public void requestAdvance(AdvanceSalaryRequestDto request) {
        Employee emp = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        AdvanceSalary advance = new AdvanceSalary();
        advance.setEmployee(emp);
        advance.setAmount(request.getAmount());
        advance.setReason(request.getReason());
        advance.setRequestDate(request.getRequestDate() != null ? request.getRequestDate() : LocalDate.now());
        advance.setStatus("PENDING");
        advanceSalaryRepository.save(advance);
    }

    @Override
    public void approveAdvance(Long advanceId, Long adminId, String status, String comments) {
        AdvanceSalary advance = advanceSalaryRepository.findById(advanceId)
                .orElseThrow(() -> new RuntimeException("Advance request not found"));
        
        Employee admin = employeeRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        advance.setStatus(status); // APPROVED, REJECTED
        advance.setComments(comments);
        advance.setApprovedBy(admin);
        if ("APPROVED".equals(status)) {
            advance.setPaymentDate(LocalDate.now());
        }
        advanceSalaryRepository.save(advance);
    }

    @Override
    public List<AdvanceSalary> getEmployeeAdvances(Long employeeId) {
        return advanceSalaryRepository.findByEmployeeId(employeeId);
    }

    @Override
    public List<AdvanceSalary> getAllPendingAdvances() {
        return advanceSalaryRepository.findByStatus("PENDING");
    }
}
