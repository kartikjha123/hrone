package com.usermanagement.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.usermanagement.requestDto.BulkProductionRequestDto;
import com.usermanagement.requestDto.ManagerProductionFilterDto;
import com.usermanagement.requestDto.MyProductionFilterDto;
import com.usermanagement.requestDto.ProductionEntryRequestDto;
import com.usermanagement.requestDto.ProductionFilterRequestDto;
import com.usermanagement.responseDto.MyProductionResponseDto;
import com.usermanagement.responseDto.ProductionEntryResponseDto;
import com.usermanagement.responseDto.ResponseMessageDto;
import com.usermanagement.service.ProductionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Production Management", description = "APIs for Production Management and Task Tracking")
@RestController
@RequestMapping("/production")
public class ProductionController {

    @Autowired
    private ProductionService productionService;

    @Operation(summary = "Create Production Entry", description = "Creates a single production entry. Triggers Manager Notification.")
    @PostMapping("/add-production-entry")
    public ResponseEntity<?> addProductionEntry(@RequestBody ProductionEntryRequestDto productionEntryRequestDto) {
        productionService.addProductionEntry(productionEntryRequestDto);
        return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "Production Entry Created Successfully"));
    }

    @Operation(summary = "Add Bulk Production Entries", description = "Submit multiple production items for approval. Triggers Manager Notification.")
    @PostMapping("/add-bulk")
    public ResponseEntity<?> addBulk(@RequestBody BulkProductionRequestDto bulkRequest) {
        productionService.addBulkProductionEntries(bulkRequest);
        return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "Bulk Entries Submitted Successfully"));
    }

    @Operation(summary = "Approve/Reject Production Task", description = "Manager approves or rejects the task. Triggers Employee Notification.")
    @PutMapping("/approve/{id}")
    public ResponseEntity<?> approve(@PathVariable Long id, @RequestParam String status, @RequestParam(required = false) String comments) {
        productionService.approveProductionEntry(id, status, comments);
        return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "Task " + status + " successfully"));
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

//    @Operation(summary = "Preview Production Payment", description = "Real-time preview of calculated payment (Qty * Rate).")
//	@GetMapping("/preview-payment")
//	public ResponseEntity<?> previewPayment(@RequestParam Long itemId, @RequestParam Integer quantity) {
//		// Preview logic handled in service or calculated here
//		return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "Payment preview calculated"));
//	}

	@Operation(summary = "Update Production Entry", description = "Updates an existing production entry")
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateProductionEntry(@PathVariable Long id,
			@RequestBody ProductionEntryRequestDto productionEntryRequestDto) {
		productionService.updateProductionEntry(id, productionEntryRequestDto);
		return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "Production Entry Updated Successfully"));
	}

	@Operation(summary = "Delete Production Entry", description = "Deletes a production entry")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteProductionEntry(@PathVariable Long id) {
		productionService.deleteProductionEntry(id);
		return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "Production Entry Deleted Successfully"));
	}
	
	@Operation(summary = "Get Manager's Team Production Entries",
	           description = "Manager apne saare employees ki production entries dekh sakta hai with filters")
	@PostMapping("/manager/entries")
	public ResponseEntity<?> getManagerEntries( @RequestBody ManagerProductionFilterDto dto) {

	    Page<ProductionEntryResponseDto> page = productionService.getEntriesByManager(dto);

	    String message = page.isEmpty() ? "No entries found" : "Entries fetched successfully";

	    return ResponseEntity.ok( new ResponseMessageDto(200, message, page));
	}
	
	
	@Operation(summary = "My Production Entries",
	           description = "Employee apni saari production entries dekh sakta hai with summary")
	@PostMapping("/my-entries")
	public ResponseEntity<?> getMyEntries(@RequestBody MyProductionFilterDto dto) {

	    MyProductionResponseDto result =
	            productionService.getMyProductionEntries(dto);

	    return ResponseEntity.ok(
	            new ResponseMessageDto(200, "My entries fetched successfully", result));
	}
	
	
	@Operation(summary = "Dashboard Summary",
	           description = "Employee ka monthly production summary")
	@GetMapping("/dashboard-summary")
	public ResponseEntity<?> getDashboardSummary(
	        @RequestParam Long employeeId,
	        @RequestParam(required = false) Integer month,
	        @RequestParam(required = false) Integer year) {

	    return ResponseEntity.ok(new ResponseMessageDto(200,
	            "Dashboard summary fetched",
	            productionService.getDashboardSummary(employeeId, month, year)));
	}

	@Operation(summary = "Monthly Payment Report",
	           description = "Employee ka monthly production aur payment report")
	@GetMapping("/monthly-report")
	public ResponseEntity<?> getMonthlyReport(
	        @RequestParam Long employeeId,
	        @RequestParam(required = false) Integer month,
	        @RequestParam(required = false) Integer year) {

	    return ResponseEntity.ok(new ResponseMessageDto(200,
	            "Monthly report fetched",
	            productionService.getMonthlyReport(employeeId, month, year)));
	}

	@Operation(summary = "Item-wise Summary",
	           description = "Konse item pe kitna kaam kiya")
	@GetMapping("/item-summary")
	public ResponseEntity<?> getItemSummary(
	        @RequestParam Long employeeId,
	        @RequestParam(required = false) LocalDate fromDate,
	        @RequestParam(required = false) LocalDate toDate) {

	    return ResponseEntity.ok(new ResponseMessageDto(200,
	            "Item summary fetched",
	            productionService.getItemWiseSummary(employeeId, fromDate, toDate)));
	}

	@Operation(summary = "Manager — Employee-wise Summary",
	           description = "Manager ke saare employees ka monthly summary")
	@GetMapping("/manager/employee-summary")
	public ResponseEntity<?> getManagerEmployeeSummary(
	        @RequestParam Long managerId,
	        @RequestParam(required = false) Integer month,
	        @RequestParam(required = false) Integer year) {

	    return ResponseEntity.ok(new ResponseMessageDto(200,
	            "Employee summary fetched",
	            productionService.getManagerEmployeeSummary(managerId, month, year)));
	}

	@Operation(summary = "Get Entry Detail",
	           description = "Single production entry ka detail")
	@GetMapping("/detail/{id}")
	public ResponseEntity<?> getEntryDetail(@PathVariable Long id) {
	    return ResponseEntity.ok(new ResponseMessageDto(200,
	            "Entry fetched",
	            productionService.getEntryById(id)));
	}

	
}
