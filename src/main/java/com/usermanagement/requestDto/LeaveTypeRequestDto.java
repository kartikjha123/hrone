package com.usermanagement.requestDto;

public class LeaveTypeRequestDto {
    private String name;
    private int maxLeavesPerYear;
    private boolean carryForwardAllowed;
    private int maxCarryForward;
    private boolean encashmentAllowed;

    public LeaveTypeRequestDto() {
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
