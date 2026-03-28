package com.usermanagement.serviceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.usermanagement.entity.Employee;
import com.usermanagement.entity.ItemMaster;
import com.usermanagement.entity.ProductionEntry;
import com.usermanagement.repository.EmployeeRepository;
import com.usermanagement.repository.ItemMasterRepository;
import com.usermanagement.repository.ProductionEntryRepository;
import com.usermanagement.requestDto.BulkProductionRequestDto;
import com.usermanagement.requestDto.ManagerProductionFilterDto;
import com.usermanagement.requestDto.MyProductionFilterDto;
import com.usermanagement.requestDto.ProductionEntryRequestDto;
import com.usermanagement.requestDto.ProductionFilterRequestDto;
import com.usermanagement.responseDto.DailyProductionGraphDto;
import com.usermanagement.responseDto.EmployeeSummaryDto;
import com.usermanagement.responseDto.ItemWiseSummaryDto;
import com.usermanagement.responseDto.MonthlyProductionGraphResponseDto;
import com.usermanagement.responseDto.MonthlyProductionReportDto;
import com.usermanagement.responseDto.MyProductionResponseDto;
import com.usermanagement.responseDto.OvertimeSummaryDto;
import com.usermanagement.responseDto.ProductionDashboardDto;
import com.usermanagement.responseDto.ProductionEntryResponseDto;
import com.usermanagement.service.NotificationService;
import com.usermanagement.service.ProductionService;

@Service
public class ProductionServiceImpl implements ProductionService {

	@Autowired
	private ItemMasterRepository itemMasterRepository;

	@Autowired
	private ProductionEntryRepository productionEntryRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private NotificationService notificationService;

	@Override
	public void addProductionEntry(ProductionEntryRequestDto productionEntryRequestDto) {
		Employee emp = employeeRepository.findById(productionEntryRequestDto.getEmployeeId())
				.orElseThrow(() -> new RuntimeException("Employee not found"));
		ItemMaster item = itemMasterRepository.findById(productionEntryRequestDto.getItemId())
				.orElseThrow(() -> new RuntimeException("Item not found"));

		ProductionEntry entry = new ProductionEntry();
		entry.setEmployee(emp);
		entry.setItem(item);
		entry.setAmount(item.getRate() * productionEntryRequestDto.getQuantity());
		entry.setWorkDate(LocalDate.now());
		entry.setQuantity(productionEntryRequestDto.getQuantity());
		entry.setStatus("PENDING");
		
		// ✅ ye add karo
		entry.setIsOvertime(
		    productionEntryRequestDto.getIsOvertime() != null 
		    && productionEntryRequestDto.getIsOvertime()
		);
		
		productionEntryRepository.save(entry);

		// Notify Manager
		if (emp.getManager() != null) {
			notificationService.sendNotification(emp.getManager(), 
				"Daily Task Submitted", 
				emp.getFirstName() + " has submitted a production entry for approval.",
				"PROD_SUBMITTED");
		}
	}

	@Override
	public void addBulkProductionEntries(BulkProductionRequestDto bulkRequest) {
		Employee emp = employeeRepository.findById(bulkRequest.getEmployeeId())
				.orElseThrow(() -> new RuntimeException("Employee not found"));

		List<ProductionEntry> entries = bulkRequest.getItems().stream().map(itemDto -> {
			ItemMaster item = itemMasterRepository.findById(itemDto.getItemId())
					.orElseThrow(() -> new RuntimeException("Item not found: " + itemDto.getItemId()));
			ProductionEntry entry = new ProductionEntry();
			entry.setEmployee(emp);
			entry.setItem(item);
			entry.setQuantity(itemDto.getQuantity());
			entry.setAmount(item.getRate() * itemDto.getQuantity());
			entry.setWorkDate(bulkRequest.getWorkDate());
			entry.setRemarks(itemDto.getRemarks());
			entry.setStatus("PENDING");
			
			// ✅ ye add karo
			entry.setIsOvertime(
			    itemDto.getIsOvertime() != null && itemDto.getIsOvertime()
			);
			
			return entry;
		}).collect(Collectors.toList());

		productionEntryRepository.saveAll(entries);

		// Notify Manager
		if (emp.getManager() != null) {
			notificationService.sendNotification(emp.getManager(), 
				"Bulk Task Submitted", 
				emp.getFirstName() + " has submitted " + entries.size() + " production entries for approval.",
				"PROD_SUBMITTED");
		}
	}

	@Override
	public void approveProductionEntry(Long entryId, String status, String comments) {
		ProductionEntry entry = productionEntryRepository.findById(entryId)
				.orElseThrow(() -> new RuntimeException("Entry not found"));
		entry.setStatus(status);
		entry.setSupervisorComments(comments);
		productionEntryRepository.save(entry);

		// Notify Employee
		notificationService.sendNotification(entry.getEmployee(), 
			"Task " + status, 
			"Your production entry for " + entry.getWorkDate() + " has been " + status,
			"PROD_STATUS_UPDATE");
	}

	@Override
	public Page<ProductionEntryResponseDto> getAllProductionEntries(ProductionFilterRequestDto req) {
		Pageable pageable = PageRequest.of(req.getPage(), req.getSize());
		// Basic implementation without full filtering logic for now
		Page<ProductionEntry> filterProductionEntries =	productionEntryRepository.filterProductionEntries(req.getEmployeeId(), req.getItemId(), req.getFromDate(), req.getToDate(), pageable);
		
		return filterProductionEntries.map(this::mapToResponseDto);
	}

	@Override
	public void updateProductionEntry(Long id, ProductionEntryRequestDto dto) {
		ProductionEntry entry = productionEntryRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Production entry not found with id: " + id));
		if (!"PENDING".equals(entry.getStatus())) {
			throw new RuntimeException("Only pending production entries can be updated");
		}
		ItemMaster item = itemMasterRepository.findById(dto.getItemId())
				.orElseThrow(() -> new RuntimeException("Item not found with id: " + dto.getItemId()));
		entry.setItem(item);
		entry.setQuantity(dto.getQuantity());
		entry.setAmount(item.getRate() * dto.getQuantity());
		productionEntryRepository.save(entry);
	}

	@Override
	public void deleteProductionEntry(Long id) {
		ProductionEntry entry = productionEntryRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Production entry not found with id: " + id));
		if (!"PENDING".equals(entry.getStatus())) {
			throw new RuntimeException("Only pending production entries can be deleted");
		}
		productionEntryRepository.delete(entry);
	}

	private ProductionEntryResponseDto mapToResponseDto(ProductionEntry entry) {
		ProductionEntryResponseDto dto = new ProductionEntryResponseDto();
		dto.setProductionId(entry.getId());
		dto.setItemName(entry.getItem().getItemName());
		dto.setQuantity(entry.getQuantity());
		dto.setAmount(entry.getAmount());
		dto.setStatus(entry.getStatus());
		dto.setWorkDate(entry.getWorkDate());
		dto.setItemId(entry.getItem().getId());
		dto.setItemName(entry.getItem().getItemName());
		dto.setRate(entry.getItem().getRate());
		dto.setEmployeeCode(entry.getEmployee().getEmployeeCode());
		dto.setEmployeeId(entry.getEmployee().getId());
		dto.setUnit(entry.getItem().getUnit());
		dto.setEmployeeName(entry.getEmployee().getFirstName() + " " + entry.getEmployee().getLastName());
		dto.setIsOvertime(entry.getIsOvertime());
		return dto;
	}

	@Override
	@Transactional(readOnly = true)
	public Page<ProductionEntryResponseDto> getEntriesByManager(ManagerProductionFilterDto dto) {

	    Pageable pageable = PageRequest.of(
	            dto.getPage() == null ? 0 : dto.getPage(),
	            dto.getSize() == null ? 10 : dto.getSize()
	    );

	    // status null ho toh sab aaye
	    String status = (dto.getStatus() != null && !dto.getStatus().isBlank())
	            ? dto.getStatus() : null;

	    return productionEntryRepository
	            .findByManagerId(dto.getManagerId(), status,
	                             dto.getFromDate(), dto.getToDate(), pageable)
	            .map(this::mapToResponseDto);
	}

	@Override
	@Transactional(readOnly = true)
	public MyProductionResponseDto getMyProductionEntries(MyProductionFilterDto dto) {

	    // Employee find karo
	    Employee emp = employeeRepository.findById(dto.getEmployeeId())
	            .orElseThrow(() -> new RuntimeException(
	                    "Employee not found with id: " + dto.getEmployeeId()));

	    Pageable pageable = PageRequest.of(
	            dto.getPage() == null ? 0 : dto.getPage(),
	            dto.getSize() == null ? 10 : dto.getSize()
	    );

	    String status = (dto.getStatus() != null && !dto.getStatus().isBlank())
	            ? dto.getStatus() : null;

	    // Entries fetch karo
	    Page<ProductionEntryResponseDto> entries = productionEntryRepository
	            .findMyEntries(
	                    dto.getEmployeeId(), status,
	                    dto.getFromDate(), dto.getToDate(), pageable)
	            .map(this::mapToResponseDto);

	    // Summary counts
	    Long totalEntries  = productionEntryRepository
	            .countByEmployeeAndStatus(dto.getEmployeeId(), null);
	    Long pendingCount  = productionEntryRepository
	            .countByEmployeeAndStatus(dto.getEmployeeId(), "PENDING");
	    Long approvedCount = productionEntryRepository
	            .countByEmployeeAndStatus(dto.getEmployeeId(), "APPROVED");
	    Long rejectedCount = productionEntryRepository
	            .countByEmployeeAndStatus(dto.getEmployeeId(), "REJECTED");
	    Double totalAmount = productionEntryRepository
	            .getTotalApprovedAmount(dto.getEmployeeId());

	    return new MyProductionResponseDto(
	            emp.getFirstName() + " " + emp.getLastName(),
	            emp.getEmployeeCode(),
	            totalEntries,
	            pendingCount,
	            approvedCount,
	            rejectedCount,
	            totalAmount,
	            entries
	    );
	}

	// ── Dashboard Summary ────────────────────────────────────────────────
	@Override
	@Transactional(readOnly = true)
	public ProductionDashboardDto getDashboardSummary(Long employeeId,
	        Integer month, Integer year) {

	    Employee emp = employeeRepository.findById(employeeId)
	            .orElseThrow(() -> new RuntimeException("Employee not found"));

	    LocalDate now = LocalDate.now();
	    int m = month != null ? month : now.getMonthValue();
	    int y = year  != null ? year  : now.getYear();

	    Long total    = productionEntryRepository
	            .countByEmployeeStatusMonthYear(employeeId, null, m, y);
	    Long pending  = productionEntryRepository
	            .countByEmployeeStatusMonthYear(employeeId, "PENDING", m, y);
	    Long approved = productionEntryRepository
	            .countByEmployeeStatusMonthYear(employeeId, "APPROVED", m, y);
	    Long rejected = productionEntryRepository
	            .countByEmployeeStatusMonthYear(employeeId, "REJECTED", m, y);

	    Double approvedAmount = productionEntryRepository
	            .sumAmountByStatusMonthYear(employeeId, "APPROVED", m, y);
	    Double pendingAmount  = productionEntryRepository
	            .sumAmountByStatusMonthYear(employeeId, "PENDING", m, y);

	    return new ProductionDashboardDto(
	            emp.getFirstName() + " " + emp.getLastName(),
	            emp.getEmployeeCode(),
	            total, pending, approved, rejected,
	            approvedAmount, pendingAmount,
	            java.time.Month.of(m).name(), y
	    );
	}

	// ── Monthly Report ───────────────────────────────────────────────────
	@Override
	@Transactional(readOnly = true)
	public MonthlyProductionReportDto getMonthlyReport(Long employeeId,
	        Integer month, Integer year) {

	    Employee emp = employeeRepository.findById(employeeId)
	            .orElseThrow(() -> new RuntimeException("Employee not found"));

	    LocalDate now = LocalDate.now();
	    int m = month != null ? month : now.getMonthValue();
	    int y = year  != null ? year  : now.getYear();

	    List<ProductionEntry> entries = productionEntryRepository
	            .findMonthlyEntries(employeeId, m, y);

	    List<ProductionEntryResponseDto> entryDtos = entries.stream()
	            .map(this::mapToResponseDto)
	            .toList();

	    double totalAmount    = entries.stream()
	            .mapToDouble(ProductionEntry::getAmount).sum();
	    double approvedAmount = entries.stream()
	            .filter(e -> "APPROVED".equals(e.getStatus()))
	            .mapToDouble(ProductionEntry::getAmount).sum();
	    double pendingAmount  = entries.stream()
	            .filter(e -> "PENDING".equals(e.getStatus()))
	            .mapToDouble(ProductionEntry::getAmount).sum();
	    double rejectedAmount = entries.stream()
	            .filter(e -> "REJECTED".equals(e.getStatus()))
	            .mapToDouble(ProductionEntry::getAmount).sum();
	    int totalQty = entries.stream()
	            .mapToInt(ProductionEntry::getQuantity).sum();

	    return new MonthlyProductionReportDto(
	            emp.getFirstName() + " " + emp.getLastName(),
	            java.time.Month.of(m).name() + " " + y,
	            (long) entries.size(),
	            totalQty,
	            totalAmount, approvedAmount,
	            pendingAmount, rejectedAmount,
	            entryDtos
	    );
	}

	// ── Item-wise Summary ────────────────────────────────────────────────
	@Override
	@Transactional(readOnly = true)
	public List<ItemWiseSummaryDto> getItemWiseSummary(Long employeeId,
	        LocalDate fromDate, LocalDate toDate) {

	    LocalDate from = fromDate != null ? fromDate
	            : LocalDate.now().withDayOfMonth(1);
	    LocalDate to   = toDate   != null ? toDate
	            : LocalDate.now();

	    return productionEntryRepository
	            .findItemWiseSummary(employeeId, from, to)
	            .stream()
	            .map(row -> new ItemWiseSummaryDto(
	                    (String) row[0],
	                    ((Number) row[1]).longValue(),
	                    ((Number) row[2]).doubleValue()
	            ))
	            .toList();
	}

	// ── Manager Employee Summary ─────────────────────────────────────────
	@Override
	@Transactional(readOnly = true)
	public List<EmployeeSummaryDto> getManagerEmployeeSummary(Long managerId,
	        Integer month, Integer year) {

	    LocalDate now = LocalDate.now();
	    int m = month != null ? month : now.getMonthValue();
	    int y = year  != null ? year  : now.getYear();

	    return productionEntryRepository
	            .findManagerEmployeeSummary(managerId, m, y)
	            .stream()
	            .map(row -> new EmployeeSummaryDto(
	                    ((Number) row[0]).longValue(),
	                    (String) row[1] + " " + (String) row[2],
	                    (String) row[3],
	                    ((Number) row[4]).longValue(),
	                    ((Number) row[5]).doubleValue(),
	                    ((Number) row[6]).doubleValue()
	            ))
	            .toList();
	}

	// ── Get Single Entry ─────────────────────────────────────────────────
	@Override
	@Transactional(readOnly = true)
	public ProductionEntryResponseDto getEntryById(Long id) {
	    ProductionEntry entry = productionEntryRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException(
	                    "Production entry not found with id: " + id));
	    return mapToResponseDto(entry);
	}

//	@Override
//	@Transactional
//	public void approveRejectEntry(ApproveRejectDto dto) {
//
//	    ProductionEntry entry = productionEntryRepository.findById(dto.getEntryId())
//	            .orElseThrow(() -> new RuntimeException(
//	                    "Production entry not found with id: " + dto.getEntryId()));
//
//	    // Sirf PENDING entries hi approve/reject ho sakti hain
//	    if (!"PENDING".equals(entry.getStatus())) {
//	        throw new RuntimeException(
//	                "Only PENDING entries can be approved or rejected. Current status: "
//	                + entry.getStatus());
//	    }
//
//	    // Status validate karo
//	    if (!List.of("APPROVED", "REJECTED").contains(dto.getStatus())) {
//	        throw new RuntimeException("Status must be APPROVED or REJECTED");
//	    }
//
//	    entry.setStatus(dto.getStatus());
//	    entry.setSupervisorComments(dto.getComments());
//	    productionEntryRepository.save(entry);
//
//	    // Employee ko notification bhejo
//	    notificationService.sendNotification(
//	            entry.getEmployee(),
//	            "Task " + dto.getStatus(),
//	            "Your production entry for " + entry.getWorkDate()
//	                    + " has been " + dto.getStatus()
//	                    + (dto.getComments() != null ? ". Comments: " + dto.getComments() : ""),
//	            "PROD_STATUS_UPDATE"
//	    );
//	}
	
	
	// ── Monthly Overtime Summary ─────────────────────────────────
	@Override
	@Transactional(readOnly = true)
	public OvertimeSummaryDto getOvertimeSummary(Long employeeId,
	        Integer month, Integer year) {

	    Employee emp = employeeRepository.findById(employeeId)
	            .orElseThrow(() -> new RuntimeException("Employee not found"));

	    LocalDate now = LocalDate.now();
	    int m = month != null ? month : now.getMonthValue();
	    int y = year  != null ? year  : now.getYear();

	    List<ProductionEntryResponseDto> entries = productionEntryRepository
	            .findOvertimeEntries(employeeId, null, m, y)
	            .stream()
	            .map(this::mapToResponseDto)
	            .toList();

	    Object[] summary = productionEntryRepository
	            .getOvertimeSummary(employeeId, m, y);

	    Long totalEntries  = summary[0] != null ? ((Number) summary[0]).longValue()  : 0L;
	    Integer totalQty   = summary[1] != null ? ((Number) summary[1]).intValue()   : 0;
	    Double totalAmount = summary[2] != null ? ((Number) summary[2]).doubleValue(): 0.0;

	    return new OvertimeSummaryDto(
	            emp.getFirstName() + " " + emp.getLastName(),
	            emp.getEmployeeCode(),
	            java.time.Month.of(m).name() + " " + y,
	            y,
	            totalEntries,
	            totalQty,
	            totalAmount,
	            entries
	    );
	}

	// ── Aaj ka Overtime Summary ──────────────────────────────────
	@Override
	@Transactional(readOnly = true)
	public OvertimeSummaryDto getTodayOvertimeSummary(Long employeeId) {

	    Employee emp = employeeRepository.findById(employeeId)
	            .orElseThrow(() -> new RuntimeException("Employee not found"));

	    LocalDate today = LocalDate.now();

	    List<ProductionEntryResponseDto> entries = productionEntryRepository
	            .findTodayOvertimeEntries(employeeId, today)
	            .stream()
	            .map(this::mapToResponseDto)
	            .toList();

	    Object[] summary = productionEntryRepository
	            .getTodayOvertimeSummary(employeeId, today);

	    Long totalEntries  = summary[0] != null ? ((Number) summary[0]).longValue()  : 0L;
	    Integer totalQty   = summary[1] != null ? ((Number) summary[1]).intValue()   : 0;
	    Double totalAmount = summary[2] != null ? ((Number) summary[2]).doubleValue(): 0.0;

	    return new OvertimeSummaryDto(
	            emp.getFirstName() + " " + emp.getLastName(),
	            emp.getEmployeeCode(),
	            "TODAY - " + today,
	            today.getYear(),
	            totalEntries,
	            totalQty,
	            totalAmount,
	            entries
	    );
	}
	
	
	@Override
	@Transactional(readOnly = true)
	public MonthlyProductionGraphResponseDto getMonthlyProductionGraph(
	        Integer month, Integer year) {

	    LocalDate now = LocalDate.now();
	    int m = month != null ? month : now.getMonthValue();
	    int y = year  != null ? year  : now.getYear();

	    // 1. ✅ Daily graph data — overtime saath
	    List<Object[]> rows = productionEntryRepository.findDailyProductionGraph(m, y);

	    List<DailyProductionGraphDto> dailyData = rows.stream().map(row -> {

	        String date         = row[0].toString();
	        int totalEntries    = ((Number) row[1]).intValue();
	        int totalQuantity   = row[2] != null ? ((Number) row[2]).intValue()    : 0;
	        double totalAmount  = row[3] != null ? ((Number) row[3]).doubleValue() : 0.0;
	        int approvedCount   = row[4] != null ? ((Number) row[4]).intValue()    : 0;
	        int pendingCount    = row[5] != null ? ((Number) row[5]).intValue()    : 0;
	        int rejectedCount   = row[6] != null ? ((Number) row[6]).intValue()    : 0;

	        // ✅ Overtime fields
	        int otEntries       = row[7] != null ? ((Number) row[7]).intValue()    : 0;
	        int otQuantity      = row[8] != null ? ((Number) row[8]).intValue()    : 0;
	        double otAmount     = row[9] != null ? ((Number) row[9]).doubleValue() : 0.0;

	        return new DailyProductionGraphDto(
	                date, totalEntries, totalQuantity, totalAmount,
	                approvedCount, pendingCount, rejectedCount,
	                otEntries, otQuantity, otAmount  // ✅ overtime saath
	        );

	    }).collect(Collectors.toList());

	    // 2. ✅ Monthly totals — overtime saath
	    
	    List<Object[]> list = productionEntryRepository.findMonthlyTotals(m, y);

	    Object[] totals = (list != null && !list.isEmpty())
	            ? list.get(0)
	            : new Object[6];

	    long totalEntries   = totals[0] != null ? ((Number) totals[0]).longValue()   : 0L;
	    int totalQuantity   = totals[1] != null ? ((Number) totals[1]).intValue()    : 0;
	    double totalAmount  = totals[2] != null ? ((Number) totals[2]).doubleValue() : 0.0;
	    long totalApproved  = totals[3] != null ? ((Number) totals[3]).longValue()   : 0L;
	    long totalPending   = totals[4] != null ? ((Number) totals[4]).longValue()   : 0L;
	    long totalRejected  = totals[5] != null ? ((Number) totals[5]).longValue()   : 0L;

	    // ✅ Overtime month totals
	    long otEntries      = totals[6] != null ? ((Number) totals[6]).longValue()   : 0L;
	    int otQuantity      = totals[7] != null ? ((Number) totals[7]).intValue()    : 0;
	    double otAmount     = totals[8] != null ? ((Number) totals[8]).doubleValue() : 0.0;

	    // 3. ✅ Response build karo
	    MonthlyProductionGraphResponseDto response = new MonthlyProductionGraphResponseDto();
	    response.setMonth(java.time.Month.of(m).name() + " " + y);
	    response.setTotalDaysWithData(dailyData.size());
	    response.setTotalEntries(totalEntries);
	    response.setTotalQuantity(totalQuantity);
	    response.setTotalAmount(totalAmount);
	    response.setTotalApproved(totalApproved);
	    response.setTotalPending(totalPending);
	    response.setTotalRejected(totalRejected);

	    // ✅ Overtime set karo
	    response.setTotalOvertimeEntries(otEntries);
	    response.setTotalOvertimeQuantity(otQuantity);
	    response.setTotalOvertimeAmount(otAmount);

	    response.setDailyData(dailyData);

	    return response;
	}
	
}
