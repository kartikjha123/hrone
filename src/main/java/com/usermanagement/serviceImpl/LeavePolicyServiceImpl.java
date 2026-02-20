package com.usermanagement.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usermanagement.entity.LeaveType;
import com.usermanagement.repository.LeaveTypeRepository;
import com.usermanagement.service.LeavePolicyService;

@Service
public class LeavePolicyServiceImpl implements LeavePolicyService {

	@Autowired
	private LeaveTypeRepository leaveTypeRepository;

	@Override
	public LeaveType createLeaveType(LeaveType leaveType) {
		// TODO Auto-generated method stub
		 return leaveTypeRepository.save(leaveType);
	}

	@Override
	public List<LeaveType> getAllLeaveTypes() {
		// TODO Auto-generated method stub
		return leaveTypeRepository.findAll();
	}

	@Override
	public LeaveType updateLeaveType(Long id, LeaveType leaveType) {
		LeaveType existingLeaveType = leaveTypeRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("LeaveType not found with id: " + id));
		existingLeaveType.setName(leaveType.getName());
		//existingLeaveType.setDefaultDays(leaveType.getDefaultDays());
		return leaveTypeRepository.save(existingLeaveType);
	}

	@Override
	public void deleteLeaveType(Long id) {
		if (!leaveTypeRepository.existsById(id)) {
			throw new RuntimeException("LeaveType not found with id: " + id);
		}
		leaveTypeRepository.deleteById(id);
	}
}
