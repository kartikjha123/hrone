package com.usermanagement.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;

import com.usermanagement.requestDto.ManagerProductionFilterDto;
import com.usermanagement.requestDto.MyProductionFilterDto;
import com.usermanagement.requestDto.ProductionEntryRequestDto;
import com.usermanagement.requestDto.ProductionFilterRequestDto;
import com.usermanagement.responseDto.EmployeeSummaryDto;
import com.usermanagement.responseDto.ItemWiseSummaryDto;
import com.usermanagement.responseDto.MonthlyProductionReportDto;
import com.usermanagement.responseDto.MyProductionResponseDto;
import com.usermanagement.responseDto.ProductionDashboardDto;
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
	
	public ProductionDashboardDto getDashboardSummary(Long employeeId,
	        Integer month, Integer year);
	
	public MonthlyProductionReportDto getMonthlyReport(Long employeeId,
	        Integer month, Integer year);
	
	public List<ItemWiseSummaryDto> getItemWiseSummary(Long employeeId,
	        LocalDate fromDate, LocalDate toDate);
	
	public List<EmployeeSummaryDto> getManagerEmployeeSummary(Long managerId,
	        Integer month, Integer year);
	
	public ProductionEntryResponseDto getEntryById(Long id);
	
	


}
