package com.usermanagement.serviceImpl;

import com.usermanagement.entity.Attendance;
import com.usermanagement.entity.Employee;
import com.usermanagement.repository.AttendanceRepository;
import com.usermanagement.repository.EmployeeRepository;
import com.usermanagement.requestDto.AttendanceRequestDto;
import com.usermanagement.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public void markAttendance(AttendanceRequestDto request) {
        Employee emp = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        Attendance attendance = new Attendance();
        attendance.setEmployee(emp);
        attendance.setDate(request.getDate() != null ? request.getDate() : LocalDate.now());
        attendance.setStatus(request.getStatus());
        attendance.setOvertimeHours(request.getOvertimeHours());
        attendance.setPunchIn(request.getPunchIn());
        attendance.setPunchOut(request.getPunchOut());
        attendanceRepository.save(attendance);
    }

    @Override
    public void bulkMarkHoliday(LocalDate date, String department) {
        List<Employee> employees;
        if (department != null && !department.isEmpty()) {
            employees = employeeRepository.findAll().stream()
                    .filter(e -> department.equals(e.getDepartment()))
                    .collect(Collectors.toList());
        } else {
            employees = employeeRepository.findAll();
        }

        List<Attendance> holidays = employees.stream().map(emp -> {
            Attendance attendance = new Attendance();
            attendance.setEmployee(emp);
            attendance.setDate(date);
            attendance.setStatus("H"); // Holiday
            return attendance;
        }).collect(Collectors.toList());

        attendanceRepository.saveAll(holidays);
    }

    @Override
    public List<Attendance> getMonthlyAttendance(Long employeeId, int month, int year) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();
        return attendanceRepository.findByEmployeeIdAndDateBetween(employeeId, startDate, endDate);
    }

    @Override
    public Double calculateMonthlyOT(Long employeeId, int month, int year) {
        List<Attendance> monthlyRecords = getMonthlyAttendance(employeeId, month, year);
        return monthlyRecords.stream()
                .mapToDouble(a -> a.getOvertimeHours() != null ? a.getOvertimeHours() : 0.0)
                .sum();
    }
}
