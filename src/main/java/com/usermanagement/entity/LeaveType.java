package com.usermanagement.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "leave_type")

public class LeaveType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private int maxLeavesPerYear;
	private boolean carryForwardAllowed;
	private int maxCarryForward;
	private boolean encashmentAllowed;
	
	public LeaveType() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LeaveType(Long id, String name, int maxLeavesPerYear, boolean carryForwardAllowed, int maxCarryForward,
			boolean encashmentAllowed) {
		super();
		this.id = id;
		this.name = name;
		this.maxLeavesPerYear = maxLeavesPerYear;
		this.carryForwardAllowed = carryForwardAllowed;
		this.maxCarryForward = maxCarryForward;
		this.encashmentAllowed = encashmentAllowed;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMaxLeavesPerYear() {
		return maxLeavesPerYear;
	}

	public void setMaxLeavesPerYear(int maxLeavesPerYear) {
		this.maxLeavesPerYear = maxLeavesPerYear;
	}

	public boolean isCarryForwardAllowed() {
		return carryForwardAllowed;
	}

	public void setCarryForwardAllowed(boolean carryForwardAllowed) {
		this.carryForwardAllowed = carryForwardAllowed;
	}

	public int getMaxCarryForward() {
		return maxCarryForward;
	}

	public void setMaxCarryForward(int maxCarryForward) {
		this.maxCarryForward = maxCarryForward;
	}

	public boolean isEncashmentAllowed() {
		return encashmentAllowed;
	}

	public void setEncashmentAllowed(boolean encashmentAllowed) {
		this.encashmentAllowed = encashmentAllowed;
	}
	
	
	
}
