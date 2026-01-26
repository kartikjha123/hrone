package com.usermanagement.responseDto;

public class ItemMasterResposneDto {

	private Long id;

	private String itemName; // CENTER RULA

	private Double rate; // 80, 180

	private String unit; // pieces, sets

	public ItemMasterResposneDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ItemMasterResposneDto(Long id, String itemName, Double rate, String unit) {
		super();
		this.id = id;
		this.itemName = itemName;
		this.rate = rate;
		this.unit = unit;
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

}
