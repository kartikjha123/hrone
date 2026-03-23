package com.usermanagement.serviceImpl;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.usermanagement.entity.Attendance;
import com.usermanagement.entity.Employee;
import com.usermanagement.repository.AttendanceRepository;
import com.usermanagement.repository.EmployeeRepository;
import com.usermanagement.requestDto.AttendanceRequestDto;
import com.usermanagement.requestDto.BulkApproveRequestDto;
import com.usermanagement.responseDto.AttendanceDayDto;
import com.usermanagement.responseDto.AttendanceStatusDto;
import com.usermanagement.responseDto.ManagerAttendanceResponseDto;
import com.usermanagement.responseDto.MyAttendanceDto;
import com.usermanagement.responseDto.OvertimeResponseDto;
import com.usermanagement.service.AttendanceService;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;

    // ✅ Constructor Injection
    public AttendanceServiceImpl(AttendanceRepository attendanceRepository,
                                  EmployeeRepository employeeRepository) {
        this.attendanceRepository = attendanceRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void punchIn(Long employeeId) {
        Employee emp = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + employeeId));

        // ✅ Double punch-in check
        Attendance existing = attendanceRepository.findByEmployeeIdAndDate(employeeId, LocalDate.now());
        if (existing != null) {
            throw new RuntimeException("Already punched in today");
        }

        Attendance attendance = new Attendance();
        attendance.setEmployee(emp);
        attendance.setDate(LocalDate.now());
        attendance.setPunchIn(LocalTime.now());
        attendance.setStatus("PP");
        attendance.setApprovalStatus("PENDING");

        attendanceRepository.save(attendance);
    }

    @Override
    public void punchOut(Long employeeId) {
        // ✅ Null check added
        Attendance attendance = attendanceRepository
                .findByEmployeeIdAndDate(employeeId, LocalDate.now());

        if (attendance == null) {
            throw new RuntimeException("No punch-in record found for today. Please punch in first.");
        }

        if (attendance.getPunchOut() != null) {
            throw new RuntimeException("Already punched out for today.");
        }
        
        LocalTime punchOut = LocalTime.now();
        attendance.setPunchOut(punchOut);
        
     // ✅ Total hours
        double totalHours = Duration.between(attendance.getPunchIn(), punchOut)
                                    .toMinutes() / 60.0;
        attendance.setTotalHours(Math.round(totalHours * 100.0) / 100.0);

        // ✅ Overtime hours (8 se zyada = overtime)
        double overtimeHours = totalHours > 8.0 ? totalHours - 8.0 : 0.0;
        attendance.setOvertimeHours(Math.round(overtimeHours * 100.0) / 100.0);

        attendanceRepository.save(attendance);
    }

    @Override
    public void updateAttendance(Long id, AttendanceRequestDto request) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));

        attendance.setDate(request.getDate());
        attendance.setStatus(request.getStatus());
        attendance.setPunchIn(request.getPunchIn());
        attendance.setPunchOut(request.getPunchOut());

        // ✅ Yeh add karo — update pe bhi recalculate ho
        if (request.getPunchIn() != null && request.getPunchOut() != null) {
            double totalHours = Duration.between(request.getPunchIn(), request.getPunchOut())
                                        .toMinutes() / 60.0;
            attendance.setTotalHours(Math.round(totalHours * 100.0) / 100.0);
            double ot = totalHours > 8.0 ? totalHours - 8.0 : 0.0;
            attendance.setOvertimeHours(Math.round(ot * 100.0) / 100.0);
        }

        attendanceRepository.save(attendance);
    }

    @Override
    public void deleteAttendance(Long id) {
        if (!attendanceRepository.existsById(id)) {
            throw new RuntimeException("Attendance record not found with id: " + id);
        }
        attendanceRepository.deleteById(id);
    }

    @Override
    public List<Attendance> getMonthlyAttendance(Long employeeId, int month, int year) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();
        return attendanceRepository.findByEmployeeIdAndDateBetween(employeeId, startDate, endDate);
    }

    @Override
    public List<ManagerAttendanceResponseDto> getManagerEmployeeAttendance(Long managerId) {

        List<Employee> employees = employeeRepository.findByManagerId(managerId);

        if (employees.isEmpty()) return List.of();

        return attendanceRepository.findByEmployeeIn(employees)
                .stream()
                .map(a -> {
                    ManagerAttendanceResponseDto dto = new ManagerAttendanceResponseDto();

                    dto.setAttendanceId(a.getId());
                    dto.setEmployeeId(a.getEmployee().getId());
                    dto.setEmployeeCode(a.getEmployee().getEmployeeCode());
                    dto.setEmployeeName(a.getEmployee().getFirstName()
                            + " " + a.getEmployee().getLastName());
                    dto.setDepartment(a.getEmployee().getDepartment());
                    dto.setDesignation(a.getEmployee().getDesignation());

                    dto.setDate(a.getDate() != null
                            ? a.getDate().toString() : null);
                    dto.setStatus(a.getStatus());
                    dto.setApprovalStatus(a.getApprovalStatus());

                    dto.setPunchIn(a.getPunchIn() != null
                            ? a.getPunchIn().toString() : null);
                    dto.setPunchOut(a.getPunchOut() != null
                            ? a.getPunchOut().toString() : null);

                    // Total hours calculate karo
                    if (a.getPunchIn() != null && a.getPunchOut() != null) {
                        long minutes = java.time.Duration
                                .between(a.getPunchIn(), a.getPunchOut())
                                .toMinutes();
                        dto.setTotalHours((minutes / 60) + "h " + (minutes % 60) + "m");
                    } else {
                        dto.setTotalHours(null);
                    }

                    return dto;
                })
                .toList();
    }

    @Override
    public void approveAttendance(Long attendanceId, Long managerId) {
        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new RuntimeException("Attendance not found with id: " + attendanceId));

        attendance.setApprovalStatus("APPROVED");
        attendance.setApprovedBy(managerId);

        attendanceRepository.save(attendance);
    }

    @Override
    public AttendanceStatusDto getTodayAttendanceStatus(Long employeeId) {
        Attendance attendance = attendanceRepository
                .findByEmployeeIdAndDate(employeeId, LocalDate.now());

        AttendanceStatusDto dto = new AttendanceStatusDto();

        if (attendance == null) {
            dto.setPunchedIn(false);
            dto.setPunchedOut(false);
            dto.setStatus("NOT_PUNCHED_IN");
            return dto;
        }

        dto.setPunchedIn(attendance.getPunchIn() != null);
        dto.setPunchedOut(attendance.getPunchOut() != null);

        if (attendance.getPunchIn() != null) {
            dto.setPunchInTime(attendance.getPunchIn().toString());
        }
        if (attendance.getPunchOut() != null) {
            dto.setPunchOutTime(attendance.getPunchOut().toString());
        }
        
     // ServiceImpl mein ye add karo
        if (attendance.getPunchIn() != null && attendance.getPunchOut() != null) {
            long minutes = java.time.Duration.between(
                attendance.getPunchIn(),
                attendance.getPunchOut()
            ).toMinutes();

            long hours = minutes / 60;
            long mins = minutes % 60;
            dto.setTotalHours(hours + "h " + mins + "m");
        }

        dto.setDate(attendance.getDate()); // ✅ date bhi set karo

        dto.setStatus(attendance.getStatus());
        return dto;
    }

    @Override
    public MyAttendanceDto getMyAttendance(Long employeeId, Integer month, Integer year) {

        // ✅ Month/Year na aaye to current month lo
        LocalDate now = LocalDate.now();
        int m = (month != null) ? month : now.getMonthValue();
        int y = (year != null) ? year : now.getYear();

        YearMonth yearMonth = YearMonth.of(y, m);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        List<Attendance> attendanceList =
            attendanceRepository.findByEmployeeIdAndDateBetween(employeeId, startDate, endDate);

        // ✅ Har din ka record banao
        List<AttendanceDayDto> records = attendanceList.stream().map(a -> {
            AttendanceDayDto day = new AttendanceDayDto();
            day.setDate(a.getDate());
            day.setStatus(a.getStatus());
            day.setApprovalStatus(a.getApprovalStatus());

            if (a.getPunchIn() != null)
                day.setPunchIn(a.getPunchIn().toString());

            if (a.getPunchOut() != null)
                day.setPunchOut(a.getPunchOut().toString());

            // ✅ Total hours calculate karo
            if (a.getPunchIn() != null && a.getPunchOut() != null) {
                long minutes = Duration.between(a.getPunchIn(), a.getPunchOut()).toMinutes();
                day.setTotalHours((minutes / 60) + "h " + (minutes % 60) + "m");
            }

            return day;
        }).collect(Collectors.toList());

        // ✅ Summary count karo
        int present = (int) attendanceList.stream()
            .filter(a -> "PP".equals(a.getStatus())).count();
        int halfDay = (int) attendanceList.stream()
            .filter(a -> "HD".equals(a.getStatus())).count();
        int absent = (int) attendanceList.stream()
            .filter(a -> "AA".equals(a.getStatus())).count();

        MyAttendanceDto dto = new MyAttendanceDto();
        dto.setMonth(yearMonth.getMonth().name() + " " + y);
        dto.setTotalWorkingDays(attendanceList.size());
        dto.setPresentDays(present);
        dto.setHalfDays(halfDay);
        dto.setAbsentDays(absent);
        dto.setRecords(records);

        return dto;
    }

	@Override
	public List<OvertimeResponseDto> getMyOvertime(Long employeeId, Integer month, Integer year) {
		 LocalDate now = LocalDate.now();
		    int m = month != null ? month : now.getMonthValue();
		    int y = year != null ? year : now.getYear();

		    return attendanceRepository
		        .findOvertimeByEmployeeAndMonth(employeeId, m, y)
		        .stream()
		        .map(this::mapToOvertimeDto)
		        .collect(Collectors.toList());
	}

	@Override
	public List<OvertimeResponseDto> getManagerOvertimeView(Long managerId) {
		 return attendanceRepository
			        .findOvertimeByManager(managerId)
			        .stream()
			        .map(this::mapToOvertimeDto)
			        .collect(Collectors.toList());
	}
	
	
	private OvertimeResponseDto mapToOvertimeDto(Attendance a) {
	    OvertimeResponseDto dto = new OvertimeResponseDto();
	    dto.setAttendanceId(a.getId());
	    dto.setEmployeeId(a.getEmployee().getId());
	    dto.setEmployeeName(a.getEmployee().getFirstName() + " " + a.getEmployee().getLastName());
	    dto.setEmployeeCode(a.getEmployee().getEmployeeCode());
	    dto.setDepartment(a.getEmployee().getDepartment());
	    dto.setDate(a.getDate() != null ? a.getDate().toString() : null);
	    dto.setPunchIn(a.getPunchIn() != null ? a.getPunchIn().toString() : null);
	    dto.setPunchOut(a.getPunchOut() != null ? a.getPunchOut().toString() : null);
	    dto.setOvertimeHours(a.getOvertimeHours());
	    dto.setApprovalStatus(a.getApprovalStatus());

	    // "9h 30m" format
	    if (a.getTotalHours() != null) {
	        long h = (long) Math.floor(a.getTotalHours());
	        long m = Math.round((a.getTotalHours() - h) * 60);
	        dto.setTotalHours(h + "h " + m + "m");
	    }
	    return dto;
	}

	// ─────────────────────────────────────────────
	// AttendanceServiceImpl mein implement karo
	@Override
	@Transactional
	public void bulkApproveAttendance(BulkApproveRequestDto request) {

	    if (request.getAttendanceIds() == null || request.getAttendanceIds().isEmpty()) {
	        throw new RuntimeException("No attendance IDs provided");
	    }

	    List<Attendance> records = attendanceRepository.findAllById(request.getAttendanceIds());

	    if (records.isEmpty()) {
	        throw new RuntimeException("No attendance records found");
	    }

	    records.forEach(a -> {
	        a.setApprovalStatus("APPROVED");
	        a.setApprovedBy(request.getManagerId());
	        a.setApprovedDate(LocalDateTime.now());
	    });

	    attendanceRepository.saveAll(records); // ✅ ek saath save
	}
}