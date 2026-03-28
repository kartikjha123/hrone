package com.usermanagement.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "employees", indexes = { @Index(name = "idx_employee_user", columnList = "user_id") })
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String firstName;
	private String lastName;
	private String phone;
	private String department;
	private String designation;
	private String employeeCode;
	private LocalDate joiningDate;
	
	// Employee.java mein ye field add karo
	@Column(name = "date_of_birth")
	private LocalDate dateOfBirth;

	@OneToOne
	@JoinColumn(name = "user_id", unique = true)
	private User user;

	@ManyToOne
	@JoinColumn(name = "manager_id")
	private Employee manager;

	 // ✅ AR fields
    @Column(name = "remaining_ar", nullable = false)
    private Integer remainingAR = 6;

    @Column(name = "ar_reset_month")
    private Integer arResetMonth;

    @Column(name = "ar_reset_year")
    private Integer arResetYear;

	// getters/setters
	public Integer getRemainingAR() {
		return remainingAR;
	}

	public void setRemainingAR(Integer remainingAR) {
		this.remainingAR = remainingAR;
	}
	public void setUser(User user) {
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public LocalDate getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(LocalDate joiningDate) {
		this.joiningDate = joiningDate;
	}

	public User getUser() {
		return user;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public Employee getManager() {
		return manager;
	}

	public void setManager(Employee manager) {
		this.manager = manager;
	}

	public Integer getArResetMonth() {
		return arResetMonth;
	}

	public void setArResetMonth(Integer arResetMonth) {
		this.arResetMonth = arResetMonth;
	}

	public Integer getArResetYear() {
		return arResetYear;
	}

	public void setArResetYear(Integer arResetYear) {
		this.arResetYear = arResetYear;
	}

	// Getter & Setter
	public LocalDate getDateOfBirth() { return dateOfBirth; }
	public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

	
	
	// equals/hashCode/toString if needed
}
