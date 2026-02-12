package com.usermanagement.serviceImpl;

import com.usermanagement.entity.AttendanceRegularization;
import com.usermanagement.entity.Employee;
import com.usermanagement.repository.AttendanceRegularizationRepository;
import com.usermanagement.repository.EmployeeRepository;
import com.usermanagement.service.AttendanceRegularizationService;
import com.usermanagement.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AttendanceRegularizationServiceImpl implements AttendanceRegularizationService {

    @Autowired
    private AttendanceRegularizationRepository arRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private NotificationService notificationService;

    @Override
    @Transactional
    public AttendanceRegularization applyAR(Long employeeId, AttendanceRegularization arRequest) {
        Employee emp = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        if (emp.getRemainingAR() <= 0) {
            throw new RuntimeException("No remaining Attendance Regularization requests allowed for this cycle.");
        }

        arRequest.setEmployee(emp);
        arRequest.setStatus("PENDING");
        arRequest.setAppliedAt(LocalDateTime.now());
        arRequest.setManager(emp.getManager());

        AttendanceRegularization savedAR = arRepository.save(arRequest);

        // Notify Manager
        if (emp.getManager() != null) {
            notificationService.sendNotification(emp.getManager(), 
                "New AR Request", 
                emp.getFirstName() + " has requested attendance regularization for " + arRequest.getMissingDate(),
                "AR_REQUEST");
        }

        return savedAR;
    }

    @Override
    @Transactional
    public void approveAR(Long arId, String status, String comment) {
        AttendanceRegularization ar = arRepository.findById(arId)
                .orElseThrow(() -> new RuntimeException("AR request not found"));

        ar.setStatus(status);
        ar.setActionDate(LocalDateTime.now());
        arRepository.save(ar);

        if ("APPROVED".equals(status)) {
            Employee emp = ar.getEmployee();
            emp.setRemainingAR(emp.getRemainingAR() - 1);
            employeeRepository.save(emp);
        }

        // Notify Employee
        notificationService.sendNotification(ar.getEmployee(), 
            "AR Request " + status, 
            "Your attendance regularization for " + ar.getMissingDate() + " has been " + status,
            "AR_STATUS_UPDATE");
    }

    @Override
    public List<AttendanceRegularization> getEmployeeARHistory(Long employeeId) {
        return arRepository.findByEmployeeId(employeeId);
    }

    @Override
    public List<AttendanceRegularization> getPendingARForManager(Long managerId) {
        return arRepository.findByManagerIdAndStatus(managerId, "PENDING");
    }
}
