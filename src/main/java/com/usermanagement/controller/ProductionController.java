package com.usermanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usermanagement.requestDto.ProductionEntryRequestDto;
import com.usermanagement.requestDto.ProductionFilterRequestDto;
import com.usermanagement.responseDto.ProductionEntryResponseDto;
import com.usermanagement.responseDto.ResponseMessageDto;
import com.usermanagement.service.ProductionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Production Management", description = "APIs for Production Management")
@RestController
@RequestMapping("/production")
public class ProductionController {

	@Autowired
	private ProductionService productionService;

	@Operation(summary = "Create Production Entry", description = "Creates a production entry for an employee by selecting item, quantity and work date. "
			+ "The amount is calculated automatically based on item rate.")
	@PostMapping("/add-production-entry")
	public ResponseEntity<?> addProductionEntry(@RequestBody ProductionEntryRequestDto productionEntryRequestDto) {

		productionService.addProductionEntry(productionEntryRequestDto);

		return ResponseEntity
				.ok(new ResponseMessageDto(HttpStatus.OK.value(), "Production Entry Created Successfully"));
	}

	@Operation(summary = "Filter Production Entries", description = "Fetch production entries based on employee, item and date range filters")
	@PostMapping("/filter")
	public ResponseEntity<?> filterProductionEntries(@RequestBody ProductionFilterRequestDto filterRequest) {

		filterRequest.setPage(filterRequest.getPage() == null ? 0 : filterRequest.getPage());
		filterRequest.setSize(filterRequest.getSize() == null ? 10 : filterRequest.getSize());
		
		 Page<ProductionEntryResponseDto> page = productionService.getAllProductionEntries(filterRequest);

		String message = page.isEmpty() ? "No Production Data Found" : "Filtered Production Data Fetched Successfully";

		return ResponseEntity.ok(new ResponseMessageDto(200, message, page));
	}

}
