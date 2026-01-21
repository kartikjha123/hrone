package com.usermanagement.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "item_master")
public class ItemMaster {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String itemName; // CENTER RULA

    @Column(nullable = false)
    private Double rate; // 80, 180

    @Column(nullable = false)
    private String unit; // pieces, sets

    private boolean active = true;
    
    @Column(name="created_date")
    private LocalDate createdDate;
    
    @Column(name="updated_date")
	private LocalDate updatedDate;
    
    @Column(name="created_by")
    private String createdBy;
   
    @Column(name="updted_by")
	private String updtedBy;
    
    

	public ItemMaster() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public ItemMaster(Long id, String itemName, Double rate, String unit, boolean active, LocalDate createdDate,
			LocalDate updatedDate, String createdBy, String updtedBy) {
		super();
		this.id = id;
		this.itemName = itemName;
		this.rate = rate;
		this.unit = unit;
		this.active = active;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
		this.createdBy = createdBy;
		this.updtedBy = updtedBy;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDate getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDate updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdtedBy() {
		return updtedBy;
	}

	public void setUpdtedBy(String updtedBy) {
		this.updtedBy = updtedBy;
	}
    
    
	

}
