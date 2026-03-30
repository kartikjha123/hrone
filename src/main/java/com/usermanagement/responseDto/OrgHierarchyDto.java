package com.usermanagement.responseDto;


import java.util.List;

public class OrgHierarchyDto {

    private Long totalEmployees;
    private Long totalManagers;
    private Long unassignedEmployees;
    private List<ManagerHierarchyDto> hierarchy;

    public static class ManagerHierarchyDto {
        private Long managerId;
        private String managerCode;
        private String managerName;
        private String managerDesignation;
        private String managerDepartment;
        private String managerEmail;
        private Long totalReportees;
        private List<EmployeeProfileDto.ReporteeDto> employees;

        // Getters & Setters
        public Long getManagerId() { return managerId; }
        public void setManagerId(Long managerId) { this.managerId = managerId; }
        public String getManagerCode() { return managerCode; }
        public void setManagerCode(String managerCode) { this.managerCode = managerCode; }
        public String getManagerName() { return managerName; }
        public void setManagerName(String managerName) { this.managerName = managerName; }
        public String getManagerDesignation() { return managerDesignation; }
        public void setManagerDesignation(String d) { this.managerDesignation = d; }
        public String getManagerDepartment() { return managerDepartment; }
        public void setManagerDepartment(String d) { this.managerDepartment = d; }
        public String getManagerEmail() { return managerEmail; }
        public void setManagerEmail(String managerEmail) { this.managerEmail = managerEmail; }
        public Long getTotalReportees() { return totalReportees; }
        public void setTotalReportees(Long totalReportees) { this.totalReportees = totalReportees; }
        public List<EmployeeProfileDto.ReporteeDto> getEmployees() { return employees; }
        public void setEmployees(List<EmployeeProfileDto.ReporteeDto> employees) { this.employees = employees; }
    }

    // Getters & Setters
    public Long getTotalEmployees() { return totalEmployees; }
    public void setTotalEmployees(Long totalEmployees) { this.totalEmployees = totalEmployees; }
    public Long getTotalManagers() { return totalManagers; }
    public void setTotalManagers(Long totalManagers) { this.totalManagers = totalManagers; }
    public Long getUnassignedEmployees() { return unassignedEmployees; }
    public void setUnassignedEmployees(Long unassignedEmployees) { this.unassignedEmployees = unassignedEmployees; }
    public List<ManagerHierarchyDto> getHierarchy() { return hierarchy; }
    public void setHierarchy(List<ManagerHierarchyDto> hierarchy) { this.hierarchy = hierarchy; }
}
