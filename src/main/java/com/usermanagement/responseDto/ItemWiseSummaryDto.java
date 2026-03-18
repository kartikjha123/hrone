package com.usermanagement.responseDto;

public class ItemWiseSummaryDto {

	private String itemName;
	private Long totalQuantity;
	private Double totalAmount;
	
	
	public ItemWiseSummaryDto() {
		super();
		// TODO Auto-generated constructor stub
	}


	public ItemWiseSummaryDto(String itemName, Long totalQuantity, Double totalAmount) {
		super();
		this.itemName = itemName;
		this.totalQuantity = totalQuantity;
		this.totalAmount = totalAmount;
	}


	public String getItemName() {
		return itemName;
	}


	public void setItemName(String itemName) {
		this.itemName = itemName;
	}


	public Long getTotalQuantity() {
		return totalQuantity;
	}


	public void setTotalQuantity(Long totalQuantity) {
		this.totalQuantity = totalQuantity;
	}


	public Double getTotalAmount() {
		return totalAmount;
	}


	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	
	

}
