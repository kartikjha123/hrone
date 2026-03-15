package com.usermanagement.responseDto;


public class LeaveBalanceDto {

    private Long id;
    private String leaveTypeName;  // sirf naam, no nested object
    private int year;
    private int totalLeaves;
    private int usedLeaves;
    private int remainingLeaves;
    private int carryForwarded;

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getLeaveTypeName() { return leaveTypeName; }
    public void setLeaveTypeName(String leaveTypeName) { this.leaveTypeName = leaveTypeName; }
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
    public int getTotalLeaves() { return totalLeaves; }
    public void setTotalLeaves(int totalLeaves) { this.totalLeaves = totalLeaves; }
    public int getUsedLeaves() { return usedLeaves; }
    public void setUsedLeaves(int usedLeaves) { this.usedLeaves = usedLeaves; }
    public int getRemainingLeaves() { return remainingLeaves; }
    public void setRemainingLeaves(int remainingLeaves) { this.remainingLeaves = remainingLeaves; }
    public int getCarryForwarded() { return carryForwarded; }
    public void setCarryForwarded(int carryForwarded) { this.carryForwarded = carryForwarded; }
}