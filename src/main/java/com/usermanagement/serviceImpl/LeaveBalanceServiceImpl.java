package com.usermanagement.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usermanagement.entity.Employee;
import com.usermanagement.entity.LeaveBalance;
import com.usermanagement.entity.LeaveType;
import com.usermanagement.repository.LeaveBalanceRepository;
import com.usermanagement.repository.LeaveTypeRepository;
import com.usermanagement.responseDto.LeaveBalanceResponseDTO;
import com.usermanagement.service.LeaveBalanceService;

@Service
public class LeaveBalanceServiceImpl implements LeaveBalanceService {

	@Autowired
	private LeaveBalanceRepository leaveBalanceRepository;

	@Autowired
	private LeaveTypeRepository leaveTypeRepository;

	@Override
	public void createLeaveBalance(Employee employee, int year) {
		 List<LeaveType> leaveTypes = leaveTypeRepository.findAll();

	        for (LeaveType type : leaveTypes) {
	            LeaveBalance lb = new LeaveBalance();
	            lb.setEmployee(employee);
	            lb.setLeaveType(type);
	            lb.setYear(year);
	            lb.setTotalLeaves(type.getMaxLeavesPerYear());
	            lb.setUsedLeaves(0);
	            lb.setRemainingLeaves(type.getMaxLeavesPerYear());
	            lb.setCarryForwarded(0);

	            leaveBalanceRepository.save(lb);
	        }
	    }

	@Override
	public void processYearEndCarryForward(int oldYear, int newYear) {
		List<LeaveBalance> oldBalances = leaveBalanceRepository.findAll().stream()
				.filter(b -> b.getYear() == oldYear)
				.toList();

		for (LeaveBalance oldBalance : oldBalances) {
			LeaveType type = oldBalance.getLeaveType();
			int carryForwardAmount = 0;

			if (type.isCarryForwardAllowed()) {
				carryForwardAmount = Math.min(oldBalance.getRemainingLeaves(), type.getMaxCarryForward());
			}

			LeaveBalance newBalance = new LeaveBalance();
			newBalance.setEmployee(oldBalance.getEmployee());
			newBalance.setLeaveType(type);
			newBalance.setYear(newYear);
			newBalance.setTotalLeaves(type.getMaxLeavesPerYear() + carryForwardAmount);
			newBalance.setUsedLeaves(0);
			newBalance.setRemainingLeaves(type.getMaxLeavesPerYear() + carryForwardAmount);
			newBalance.setCarryForwarded(carryForwardAmount);

			leaveBalanceRepository.save(newBalance);
		}
	}

	@Override
	public List<LeaveBalanceResponseDTO> getLeaveBalance(Long employeeId, int year) {
		  List<LeaveBalance> balances = leaveBalanceRepository.findByEmployeeIdAndYear(employeeId, year);

		    return balances.stream().map(lb -> new LeaveBalanceResponseDTO(
		            lb.getLeaveType().getName(),
		            lb.getYear(),
		            lb.getTotalLeaves(),
		            lb.getUsedLeaves(),
		            lb.getRemainingLeaves(),
		            lb.getCarryForwarded()
		    )).toList();
		}
	}

	
	
	


