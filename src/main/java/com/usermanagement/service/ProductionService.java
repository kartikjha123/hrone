package com.usermanagement.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.usermanagement.requestDto.ProductionEntryRequestDto;
import com.usermanagement.requestDto.ProductionFilterRequestDto;
import com.usermanagement.responseDto.ProductionEntryResponseDto;

public interface ProductionService {
	
public void addProductionEntry(ProductionEntryRequestDto productionEntryRequestDto);
	
public Page<ProductionEntryResponseDto> getAllProductionEntries(ProductionFilterRequestDto req);

}
