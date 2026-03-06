package com.usermanagement.responseDto;



public class LeaveBalanceResponseDTO {

    private String leaveType;
    private int year;
    private int totalLeaves;
    private int usedLeaves;
    private int remainingLeaves;
    private int carryForwarded;

    public LeaveBalanceResponseDTO() {}

    public LeaveBalanceResponseDTO(String leaveType, int year, int totalLeaves, int usedLeaves, int remainingLeaves, int carryForwarded) {
        this.leaveType = leaveType;
        this.year = year;
        this.totalLeaves = totalLeaves;
        this.usedLeaves = usedLeaves;
        this.remainingLeaves = remainingLeaves;
        this.carryForwarded = carryForwarded;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
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