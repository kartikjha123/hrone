package com.usermanagement.serviceImpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usermanagement.entity.Attendance;
import com.usermanagement.entity.Employee;
import com.usermanagement.repository.AttendanceRepository;
import com.usermanagement.repository.EmployeeRepository;
import com.usermanagement.requestDto.AttendanceRequestDto;
import com.usermanagement.responseDto.AttendanceStatusDto;
import com.usermanagement.service.AttendanceService;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

  

   

    @Override
    public List<Attendance> getMonthlyAttendance(Long employeeId, int month, int year) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();
        return attendanceRepository.findByEmployeeIdAndDateBetween(employeeId, startDate, endDate);
    }

  

    @Override
    public void updateAttendance(Long id, AttendanceRequestDto request) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendance record not found with id: " + id));
        attendance.setDate(request.getDate());
        attendance.setStatus(request.getStatus());
      //  attendance.setOvertimeHours(request.getOvertimeHours());
        attendance.setPunchIn(request.getPunchIn());
        attendance.setPunchOut(request.getPunchOut());
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
	public void punchIn(Long employeeId) {

		Employee emp = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new RuntimeException("Employee not found"));

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
		 Attendance attendance = attendanceRepository
		            .findByEmployeeIdAndDate(employeeId, LocalDate.now());

		    attendance.setPunchOut(LocalTime.now());

		    attendanceRepository.save(attendance);
		
	}

	@Override
	public List<Attendance> getManagerEmployeeAttendance(Long managerId) {
		List<Employee> employees = employeeRepository.findByManagerId(managerId);

	    return attendanceRepository.findByEmployeeIn(employees);
	}

	@Override
	public void approveAttendance(Long attendanceId, Long managerId) {
		// TODO Auto-generated method stub
		
		Attendance attendance = attendanceRepository.findById(attendanceId)
	            .orElseThrow(() -> new RuntimeException("Attendance not found"));

	    attendance.setApprovalStatus("APPROVED");
	    attendance.setApprovedBy(managerId);

	    attendanceRepository.save(attendance);
		
	}



	@Override
	public AttendanceStatusDto getTodayAttendanceStatus(Long employeeId) {

	    Attendance attendance =
	            attendanceRepository.findByEmployeeIdAndDate(
	                    employeeId,
	                    LocalDate.now()
	            );

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

	    dto.setStatus(attendance.getStatus());

	    return dto;
	}
	
		
	}

