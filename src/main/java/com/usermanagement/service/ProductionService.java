package com.usermanagement.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.usermanagement.requestDto.ProductionEntryRequestDto;
import com.usermanagement.requestDto.ProductionFilterRequestDto;
import com.usermanagement.responseDto.ProductionEntryResponseDto;

public interface ProductionService {
	
public void addProductionEntry(ProductionEntryRequestDto productionEntryRequestDto);
	public void addBulkProductionEntries(com.usermanagement.requestDto.BulkProductionRequestDto bulkRequest);
	public void approveProductionEntry(Long entryId, String status, String comments);
	public Page<ProductionEntryResponseDto> getAllProductionEntries(ProductionFilterRequestDto req);

}
