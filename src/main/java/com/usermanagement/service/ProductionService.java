package com.usermanagement.service;

import org.springframework.data.domain.Page;

import com.usermanagement.requestDto.ManagerProductionFilterDto;
import com.usermanagement.requestDto.MyProductionFilterDto;
import com.usermanagement.requestDto.ProductionEntryRequestDto;
import com.usermanagement.requestDto.ProductionFilterRequestDto;
import com.usermanagement.responseDto.MyProductionResponseDto;
import com.usermanagement.responseDto.ProductionEntryResponseDto;

public interface ProductionService {
	
public void addProductionEntry(ProductionEntryRequestDto productionEntryRequestDto);
	public void addBulkProductionEntries(com.usermanagement.requestDto.BulkProductionRequestDto bulkRequest);
	public void approveProductionEntry(Long entryId, String status, String comments);
	public Page<ProductionEntryResponseDto> getAllProductionEntries(ProductionFilterRequestDto req);

	public void updateProductionEntry(Long id, ProductionEntryRequestDto productionEntryRequestDto);

	public void deleteProductionEntry(Long id);
	
	Page<ProductionEntryResponseDto> getEntriesByManager(ManagerProductionFilterDto dto);
	//void approveRejectEntry(ApproveRejectDto dto);
	
	
	public MyProductionResponseDto getMyProductionEntries(MyProductionFilterDto dto);


}
