package com.usermanagement.responseDto;



import java.util.List;

public class ManagerDashboardResponseDto {

    private Long managerId;
    private String managerName;
    private long totalEmployees;
    private List<EmployeeResponseDto> employees;

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public long getTotalEmployees() {
        return totalEmployees;
    }

    public void setTotalEmployees(long totalEmployees) {
        this.totalEmployees = totalEmployees;
    }

    public List<EmployeeResponseDto> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeResponseDto> employees) {
        this.employees = employees;
    }
}