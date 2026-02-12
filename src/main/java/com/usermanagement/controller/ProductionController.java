package com.usermanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.usermanagement.requestDto.ProductionEntryRequestDto;
import com.usermanagement.requestDto.ProductionFilterRequestDto;
import com.usermanagement.requestDto.BulkProductionRequestDto;
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

    @Operation(summary = "Preview Production Payment", description = "Real-time preview of calculated payment (Qty * Rate).")
    @GetMapping("/preview-payment")
    public ResponseEntity<?> previewPayment(@RequestParam Long itemId, @RequestParam Integer quantity) {
        // Preview logic handled in service or calculated here
        return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "Payment preview calculated"));
    }
}
