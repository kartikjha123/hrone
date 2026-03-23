package com.usermanagement.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "attendance_regularization",
    indexes = {
        @Index(name = "idx_ar_employee", columnList = "employee_id"),
        @Index(name = "idx_ar_manager",  columnList = "manager_id"),
        @Index(name = "idx_ar_status",   columnList = "status")
    })
public class AttendanceRegularization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private Employee manager;

    @Column(name = "missing_date", nullable = false)
    private LocalDate missingDate;

    @Column(name = "reason", nullable = false, length = 500)
    private String reason;

    @Column(name = "requested_punch_in")
    private LocalTime requestedPunchIn;

    @Column(name = "requested_punch_out")
    private LocalTime requestedPunchOut;

    @Column(name = "status", nullable = false)
    private String status; // PENDING, APPROVED, REJECTED

    @Column(name = "manager_remarks", length = 500)
    private String managerRemarks;

    @Column(name = "ar_month", nullable = false)
    private Integer arMonth;

    @Column(name = "ar_year", nullable = false)
    private Integer arYear;

    @Column(name = "applied_at")
    private LocalDateTime appliedAt;

    @Column(name = "action_date")
    private LocalDateTime actionDate;

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }
    public Employee getManager() { return manager; }
    public void setManager(Employee manager) { this.manager = manager; }
    public LocalDate getMissingDate() { return missingDate; }
    public void setMissingDate(LocalDate missingDate) { this.missingDate = missingDate; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public LocalTime getRequestedPunchIn() { return requestedPunchIn; }
    public void setRequestedPunchIn(LocalTime requestedPunchIn) { this.requestedPunchIn = requestedPunchIn; }
    public LocalTime getRequestedPunchOut() { return requestedPunchOut; }
    public void setRequestedPunchOut(LocalTime requestedPunchOut) { this.requestedPunchOut = requestedPunchOut; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getManagerRemarks() { return managerRemarks; }
    public void setManagerRemarks(String managerRemarks) { this.managerRemarks = managerRemarks; }
    public Integer getArMonth() { return arMonth; }
    public void setArMonth(Integer arMonth) { this.arMonth = arMonth; }
    public Integer getArYear() { return arYear; }
    public void setArYear(Integer arYear) { this.arYear = arYear; }
    public LocalDateTime getAppliedAt() { return appliedAt; }
    public void setAppliedAt(LocalDateTime appliedAt) { this.appliedAt = appliedAt; }
    public LocalDateTime getActionDate() { return actionDate; }
    public void setActionDate(LocalDateTime actionDate) { this.actionDate = actionDate; }
}
