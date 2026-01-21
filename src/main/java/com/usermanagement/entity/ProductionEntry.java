package com.usermanagement.entity;

import java.time.LocalDate;

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
@Table(name = "production_entry", indexes = { @Index(name = "idx_prod_emp", columnList = "employee_id"),
		@Index(name = "idx_prod_item", columnList = "item_id"),
		@Index(name = "idx_prod_date", columnList = "work_date") })
public class ProductionEntry {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// MANY entries belong to ONE employee
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employee_id", nullable = false)
	private Employee employee;

	// MANY entries belong to ONE item
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id", nullable = false)
	private ItemMaster item;

	@Column(name = "work_date", nullable = false)
	private LocalDate workDate;

	@Column(nullable = false)
	private Integer quantity;

	@Column(nullable = false)
	private Double amount; // quantity * item.rate

	private String remarks;
	
	

	public ProductionEntry() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public ProductionEntry(Long id, Employee employee, ItemMaster item, LocalDate workDate, Integer quantity,
			Double amount, String remarks) {
		super();
		this.id = id;
		this.employee = employee;
		this.item = item;
		this.workDate = workDate;
		this.quantity = quantity;
		this.amount = amount;
		this.remarks = remarks;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public ItemMaster getItem() {
		return item;
	}

	public void setItem(ItemMaster item) {
		this.item = item;
	}

	public LocalDate getWorkDate() {
		return workDate;
	}

	public void setWorkDate(LocalDate workDate) {
		this.workDate = workDate;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
	
}
