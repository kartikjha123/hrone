package com.usermanagement.responseDto;

public class SalaryStructureResponseDto {
    private Long id;
    private Double basic;
    private Double hra;
    private Double da;
    private Double otherAllowances;
    private Double pfDeduction;
    private Double esiDeduction;
    
    // Sirf employee ki basic info
    private String employeeName;
    private String employeeCode;
    private String department;

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Double getBasic() { return basic; }
    public void setBasic(Double basic) { this.basic = basic; }
    public Double getHra() { return hra; }
    public void setHra(Double hra) { this.hra = hra; }
    public Double getDa() { return da; }
    public void setDa(Double da) { this.da = da; }
    public Double getOtherAllowances() { return otherAllowances; }
    public void setOtherAllowances(Double v) { this.otherAllowances = v; }
    public Double getPfDeduction() { return pfDeduction; }
    public void setPfDeduction(Double v) { this.pfDeduction = v; }
    public Double getEsiDeduction() { return esiDeduction; }
    public void setEsiDeduction(Double v) { this.esiDeduction = v; }
    public String getEmployeeName() { return employeeName; }
    public void setEmployeeName(String n) { this.employeeName = n; }
    public String getEmployeeCode() { return employeeCode; }
    public void setEmployeeCode(String c) { this.employeeCode = c; }
    public String getDepartment() { return department; }
    public void setDepartment(String d) { this.department = d; }
}