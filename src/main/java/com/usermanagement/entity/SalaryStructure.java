package com.usermanagement.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "salary_structures")
public class SalaryStructure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    private Double basic;
    private Double hra;
    private Double da;
    private Double otherAllowances;
    private Double pfDeduction;
    private Double esiDeduction;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }
    public Double getBasic() { return basic; }
    public void setBasic(Double basic) { this.basic = basic; }
    public Double getHra() { return hra; }
    public void setHra(Double hra) { this.hra = hra; }
    public Double getDa() { return da; }
    public void setDa(Double da) { this.da = da; }
    public Double getOtherAllowances() { return otherAllowances; }
    public void setOtherAllowances(Double otherAllowances) { this.otherAllowances = otherAllowances; }
    public Double getPfDeduction() { return pfDeduction; }
    public void setPfDeduction(Double pfDeduction) { this.pfDeduction = pfDeduction; }
    public Double getEsiDeduction() { return esiDeduction; }
    public void setEsiDeduction(Double esiDeduction) { this.esiDeduction = esiDeduction; }
}
