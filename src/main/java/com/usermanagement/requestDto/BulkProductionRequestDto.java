package com.usermanagement.requestDto;

import java.time.LocalDate;
import java.util.List;

public class BulkProductionRequestDto {
    private Long employeeId;
    private LocalDate workDate;
    private List<BulkProductionItemDto> items;

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDate getWorkDate() {
        return workDate;
    }

    public void setWorkDate(LocalDate workDate) {
        this.workDate = workDate;
    }

    public List<BulkProductionItemDto> getItems() {
        return items;
    }

    public void setItems(List<BulkProductionItemDto> items) {
        this.items = items;
    }
}
