package com.usermanagement.controller;



import com.usermanagement.responseDto.ResponseMessageDto;
import com.usermanagement.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Reports", description = "Role-based Reports for Manager and SuperAdmin")
@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @Operation(
        summary = "Manager Report",
        description = "Manager apni team ka — attendance, production, overtime, leave summary dekhe"
    )
    @GetMapping("/manager/{managerId}")
    public ResponseEntity<?> getManagerReport(
            @PathVariable Long managerId,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year) {

        return ResponseEntity.ok(
            new ResponseMessageDto(
                HttpStatus.OK.value(),
                "Manager report fetched",
                reportService.getManagerReport(managerId, month, year)
            )
        );
    }

    @Operation(
        summary = "SuperAdmin Report",
        description = "Pure company ka — attendance, production, overtime, leave + department-wise summary"
    )
    @GetMapping("/superadmin")
    public ResponseEntity<?> getSuperAdminReport(
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year) {

        return ResponseEntity.ok(
            new ResponseMessageDto(
                HttpStatus.OK.value(),
                "SuperAdmin report fetched",
                reportService.getSuperAdminReport(month, year)
            )
        );
    }
}