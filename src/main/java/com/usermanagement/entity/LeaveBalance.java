package com.usermanagement.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "leave_balance")
public class LeaveBalance {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Employee employee;

	@ManyToOne
	private LeaveType leaveType;

	private int year;
	private int totalLeaves;
	private int usedLeaves;
	private int remainingLeaves;
	private int carryForwarded;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public LeaveType getLeaveType() {
		return leaveType;
	}
	public void setLeaveType(LeaveType leaveType) {
		this.leaveType = leaveType;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getTotalLeaves() {
		return totalLeaves;
	}
	public void setTotalLeaves(int totalLeaves) {
		this.totalLeaves = totalLeaves;
	}
	public int getUsedLeaves() {
		return usedLeaves;
	}
	public void setUsedLeaves(int usedLeaves) {
		this.usedLeaves = usedLeaves;
	}
	public int getRemainingLeaves() {
		return remainingLeaves;
	}
	public void setRemainingLeaves(int remainingLeaves) {
		this.remainingLeaves = remainingLeaves;
	}
	public int getCarryForwarded() {
		return carryForwarded;
	}
	public void setCarryForwarded(int carryForwarded) {
		this.carryForwarded = carryForwarded;
	}
	
	
}
