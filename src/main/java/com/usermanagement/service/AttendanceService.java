package com.usermanagement.service;

import com.usermanagement.entity.Attendance;
import com.usermanagement.requestDto.AttendanceRequestDto;
import java.time.LocalDate;
import java.util.List;

public interface AttendanceService {
    void markAttendance(AttendanceRequestDto request);
    void bulkMarkHoliday(LocalDate date, String department);
    List<Attendance> getMonthlyAttendance(Long employeeId, int month, int year);
    Double calculateMonthlyOT(Long employeeId, int month, int year);

    void updateAttendance(Long id, AttendanceRequestDto request);

    void deleteAttendance(Long id);
}
