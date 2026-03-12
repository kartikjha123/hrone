package com.usermanagement.service;

import com.usermanagement.entity.Attendance;
import com.usermanagement.requestDto.AttendanceRequestDto;
import java.time.LocalDate;
import java.util.List;

public interface AttendanceService {
    
    List<Attendance> getMonthlyAttendance(Long employeeId, int month, int year);
   
    void updateAttendance(Long id, AttendanceRequestDto request);

    void deleteAttendance(Long id);
    
    public void punchIn(Long employeeId);
    public void punchOut(Long employeeId);
    
    public List<Attendance> getManagerEmployeeAttendance(Long managerId);
    
    public void approveAttendance(Long attendanceId, Long managerId);
}
