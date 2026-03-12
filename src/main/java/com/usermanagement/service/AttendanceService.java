package com.usermanagement.service;

import java.util.List;

import com.usermanagement.entity.Attendance;
import com.usermanagement.requestDto.AttendanceRequestDto;
import com.usermanagement.responseDto.AttendanceStatusDto;

public interface AttendanceService {

	List<Attendance> getMonthlyAttendance(Long employeeId, int month, int year);

	void updateAttendance(Long id, AttendanceRequestDto request);

	void deleteAttendance(Long id);

	public void punchIn(Long employeeId);

	public void punchOut(Long employeeId);

	public List<Attendance> getManagerEmployeeAttendance(Long managerId);

	public void approveAttendance(Long attendanceId, Long managerId);

	public AttendanceStatusDto getTodayAttendanceStatus(Long employeeId);
}
