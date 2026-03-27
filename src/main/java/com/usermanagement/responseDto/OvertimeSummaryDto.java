package com.usermanagement.responseDto;



import java.util.List;

public class OvertimeSummaryDto {

    private String employeeName;
    private String employeeCode;
    private String month;
    private Integer year;
    private Long totalOvertimeEntries;
    private Integer totalOvertimeQuantity;
    private Double totalOvertimeAmount;
    private List<ProductionEntryResponseDto> overtimeEntries;

    public OvertimeSummaryDto() {}

    public OvertimeSummaryDto(String employeeName, String employeeCode,
                               String month, Integer year,
                               Long totalOvertimeEntries,
                               Integer totalOvertimeQuantity,
                               Double totalOvertimeAmount,
                               List<ProductionEntryResponseDto> overtimeEntries) {
        this.employeeName = employeeName;
        this.employeeCode = employeeCode;
        this.month = month;
        this.year = year;
        this.totalOvertimeEntries = totalOvertimeEntries;
        this.totalOvertimeQuantity = totalOvertimeQuantity;
        this.totalOvertimeAmount = totalOvertimeAmount;
        this.overtimeEntries = overtimeEntries;
    }

    public String getEmployeeName() { return employeeName; }
    public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }

    public String getEmployeeCode() { return employeeCode; }
    public void setEmployeeCode(String employeeCode) { this.employeeCode = employeeCode; }

    public String getMonth() { return month; }
    public void setMonth(String month) { this.month = month; }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }

    public Long getTotalOvertimeEntries() { return totalOvertimeEntries; }
    public void setTotalOvertimeEntries(Long totalOvertimeEntries) { this.totalOvertimeEntries = totalOvertimeEntries; }

    public Integer getTotalOvertimeQuantity() { return totalOvertimeQuantity; }
    public void setTotalOvertimeQuantity(Integer totalOvertimeQuantity) { this.totalOvertimeQuantity = totalOvertimeQuantity; }

    public Double getTotalOvertimeAmount() { return totalOvertimeAmount; }
    public void setTotalOvertimeAmount(Double totalOvertimeAmount) { this.totalOvertimeAmount = totalOvertimeAmount; }

    public List<ProductionEntryResponseDto> getOvertimeEntries() { return overtimeEntries; }
    public void setOvertimeEntries(List<ProductionEntryResponseDto> overtimeEntries) { this.overtimeEntries = overtimeEntries; }
}
