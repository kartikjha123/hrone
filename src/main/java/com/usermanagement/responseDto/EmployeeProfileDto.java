package com.usermanagement.responseDto;



import java.time.LocalDate;
import java.util.List;

public class EmployeeProfileDto {

    // Basic Info
    private Long employeeId;
    private String employeeCode;
    private String firstName;
    private String lastName;
    private String fullName;
    private String phone;
    private String department;
    private String designation;
    private LocalDate joiningDate;
    private LocalDate dateOfBirth;
    private Integer remainingAR;

    // User Info
    private Long userId;
    private String username;
    private String email;
    private List<String> roles;

    // Manager Info (Reporting To)
    private Long managerId;
    private String managerCode;
    private String managerName;
    private String managerDesignation;
    private String managerDepartment;

    // Team Info (Employees under this person)
    private Long totalReportees;
    private List<ReporteeDto> reportees;

    // ─── Inner DTO ───────────────────────────────
    public static class ReporteeDto {
        private Long employeeId;
        private String employeeCode;
        private String fullName;
        private String designation;
        private String department;

        // Getters & Setters
        public Long getEmployeeId() { return employeeId; }
        public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }
        public String getEmployeeCode() { return employeeCode; }
        public void setEmployeeCode(String employeeCode) { this.employeeCode = employeeCode; }
        public String getFullName() { return fullName; }
        public void setFullName(String fullName) { this.fullName = fullName; }
        public String getDesignation() { return designation; }
        public void setDesignation(String designation) { this.designation = designation; }
        public String getDepartment() { return department; }
        public void setDepartment(String department) { this.department = department; }
    }

    // Getters & Setters
    public Long getEmployeeId() { return employeeId; }
    public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }
    public String getEmployeeCode() { return employeeCode; }
    public void setEmployeeCode(String employeeCode) { this.employeeCode = employeeCode; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }
    public LocalDate getJoiningDate() { return joiningDate; }
    public void setJoiningDate(LocalDate joiningDate) { this.joiningDate = joiningDate; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public Integer getRemainingAR() { return remainingAR; }
    public void setRemainingAR(Integer remainingAR) { this.remainingAR = remainingAR; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public List<String> getRoles() { return roles; }
    public void setRoles(List<String> roles) { this.roles = roles; }
    public Long getManagerId() { return managerId; }
    public void setManagerId(Long managerId) { this.managerId = managerId; }
    public String getManagerCode() { return managerCode; }
    public void setManagerCode(String managerCode) { this.managerCode = managerCode; }
    public String getManagerName() { return managerName; }
    public void setManagerName(String managerName) { this.managerName = managerName; }
    public String getManagerDesignation() { return managerDesignation; }
    public void setManagerDesignation(String managerDesignation) { this.managerDesignation = managerDesignation; }
    public String getManagerDepartment() { return managerDepartment; }
    public void setManagerDepartment(String managerDepartment) { this.managerDepartment = managerDepartment; }
    public Long getTotalReportees() { return totalReportees; }
    public void setTotalReportees(Long totalReportees) { this.totalReportees = totalReportees; }
    public List<ReporteeDto> getReportees() { return reportees; }
    public void setReportees(List<ReporteeDto> reportees) { this.reportees = reportees; }
}