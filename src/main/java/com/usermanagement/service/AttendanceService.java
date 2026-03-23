package com.usermanagement.service;

import java.util.List;

import com.usermanagement.entity.Attendance;
import com.usermanagement.requestDto.AttendanceRequestDto;
import com.usermanagement.requestDto.BulkApproveRequestDto;
import com.usermanagement.responseDto.AttendanceStatusDto;
import com.usermanagement.responseDto.ManagerAttendanceResponseDto;
import com.usermanagement.responseDto.MyAttendanceDto;
import com.usermanagement.responseDto.OvertimeResponseDto;

public interface AttendanceService {

	List<Attendance> getMonthlyAttendance(Long employeeId, int month, int year);

	void updateAttendance(Long id, AttendanceRequestDto request);

	void deleteAttendance(Long id);

	public void punchIn(Long employeeId);

	public void punchOut(Long employeeId);

	//public List<Attendance> getManagerEmployeeAttendance(Long managerId);

	public void approveAttendance(Long attendanceId, Long managerId);

	public AttendanceStatusDto getTodayAttendanceStatus(Long employeeId);
	
	MyAttendanceDto getMyAttendance(Long employeeId, Integer month, Integer year);
	
	List<ManagerAttendanceResponseDto> getManagerEmployeeAttendance(Long managerId);
	
	
	// AttendanceService.java interface mein add karo
	List<OvertimeResponseDto> getMyOvertime(Long employeeId, Integer month, Integer year);
	List<OvertimeResponseDto> getManagerOvertimeView(Long managerId);
	
	// AttendanceService interface mein add karo
	void bulkApproveAttendance(BulkApproveRequestDto request);
}
