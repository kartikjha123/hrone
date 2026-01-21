package com.usermanagement.requestDto;

public class ItemMasterRequestDto {

	private String itemName; // CENTER RULA

	private Double rate; // 80, 180

	private String unit; // pieces, sets

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

	public ItemMasterRequestDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ItemMasterRequestDto(String itemName, Double rate, String unit) {
		super();
		this.itemName = itemName;
		this.rate = rate;
		this.unit = unit;
	}

}
