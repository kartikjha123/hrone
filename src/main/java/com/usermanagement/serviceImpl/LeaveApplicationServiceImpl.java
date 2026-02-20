package com.usermanagement.serviceImpl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usermanagement.entity.Employee;
import com.usermanagement.entity.LeaveApplication;
import com.usermanagement.entity.LeaveBalance;
import com.usermanagement.entity.LeaveType;
import com.usermanagement.repository.EmployeeRepository;
import com.usermanagement.repository.LeaveApplicationRepository;
import com.usermanagement.repository.LeaveBalanceRepository;
import com.usermanagement.repository.LeaveTypeRepository;
import com.usermanagement.requestDto.LeaveRequestDto;
import com.usermanagement.service.LeaveApplicationService;

import jakarta.transaction.Transactional;

@Service
public class LeaveApplicationServiceImpl implements LeaveApplicationService {

	@Autowired
	private LeaveApplicationRepository leaveApplicationRepository;

	@Autowired
	private LeaveBalanceRepository leaveBalanceRepository;
	
	@Autowired
	private LeaveTypeRepository leaveTypeRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	@Transactional
	public LeaveApplication applyLeave(LeaveRequestDto leaveRequestDto) {
		int days = (int) ChronoUnit.DAYS.between(leaveRequestDto.getFromDate(), leaveRequestDto.getToDate()) + 1;
		int year = leaveRequestDto.getFromDate().getYear();

		LeaveBalance balance = leaveBalanceRepository
				.findByEmployeeIdAndLeaveTypeIdAndYear(leaveRequestDto.getEmployeId(), leaveRequestDto.getLeaveTypeId(), year).orElseThrow();

		if (balance.getRemainingLeaves() < days) {
			throw new RuntimeException("Insufficient leave balance");
		}
		
	Optional<Employee> employee = employeeRepository.findById(leaveRequestDto.getEmployeId());
	Optional<LeaveType> LeaveType = leaveTypeRepository.findById(leaveRequestDto.getLeaveTypeId());

		LeaveApplication app = new LeaveApplication();
		app.setEmployee(employee.get());
		app.setLeaveType(LeaveType.get());
		app.setFromDate(leaveRequestDto.getFromDate());
		app.setToDate(leaveRequestDto.getToDate());
		app.setTotalDays(days);
		app.setReason(leaveRequestDto.getReason());
		app.setManagerId(employee.get().getManager().getId());

		app.setStatus("PENDING");
		app.setAppliedAt(LocalDateTime.now());

		return leaveApplicationRepository.save(app);
	}

	@Override
	public List<LeaveApplication> getAllApplyLeaveByEmployee(Long employeeId) {
		return leaveApplicationRepository.findByEmployeeId(employeeId);
	}

	@Override
	public void cancelLeave(Long id) {
		LeaveApplication leaveApplication = leaveApplicationRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Leave application not found with id: " + id));
		if (!"PENDING".equals(leaveApplication.getStatus())) {
			throw new RuntimeException("Only pending leave applications can be cancelled");
		}
		leaveApplicationRepository.delete(leaveApplication);
	}
}
