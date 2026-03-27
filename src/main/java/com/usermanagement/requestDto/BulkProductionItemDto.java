package com.usermanagement.requestDto;

public class BulkProductionItemDto {
    private Long itemId;
    private Integer quantity;
    private String remarks;
    
    private Boolean isOvertime = false;

    public Boolean getIsOvertime() {
        return isOvertime;
    }
    public void setIsOvertime(Boolean isOvertime) {
        this.isOvertime = isOvertime;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
