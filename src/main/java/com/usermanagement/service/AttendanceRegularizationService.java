package com.usermanagement.service;

import com.usermanagement.entity.AttendanceRegularization;
import java.util.List;

public interface AttendanceRegularizationService {
    AttendanceRegularization applyAR(Long employeeId, AttendanceRegularization arRequest);
    void approveAR(Long arId, String status, String comment);
    List<AttendanceRegularization> getEmployeeARHistory(Long employeeId);
    List<AttendanceRegularization> getPendingARForManager(Long managerId);
}
