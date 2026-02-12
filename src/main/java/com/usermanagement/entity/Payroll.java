package com.usermanagement.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "payrolls")
public class Payroll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    private Integer month;
    private Integer year;
    private Double productionEarnings; // From Image 1 logic
    private Double baseSalary;
    private Double otEarnings;
    private Double advanceDeduction; // From Image 2 logic
    private Double netSalary;
    private LocalDate processedDate;
    private String status; // DRAFT, PAID

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }
    public Integer getMonth() { return month; }
    public void setMonth(Integer month) { this.month = month; }
    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }
    public Double getProductionEarnings() { return productionEarnings; }
    public void setProductionEarnings(Double productionEarnings) { this.productionEarnings = productionEarnings; }
    public Double getBaseSalary() { return baseSalary; }
    public void setBaseSalary(Double baseSalary) { this.baseSalary = baseSalary; }
    public Double getOtEarnings() { return otEarnings; }
    public void setOtEarnings(Double otEarnings) { this.otEarnings = otEarnings; }
    public Double getAdvanceDeduction() { return advanceDeduction; }
    public void setAdvanceDeduction(Double advanceDeduction) { this.advanceDeduction = advanceDeduction; }
    public Double getNetSalary() { return netSalary; }
    public void setNetSalary(Double netSalary) { this.netSalary = netSalary; }
    public LocalDate getProcessedDate() { return processedDate; }
    public void setProcessedDate(LocalDate processedDate) { this.processedDate = processedDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
