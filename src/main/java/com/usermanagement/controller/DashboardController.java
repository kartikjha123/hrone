package com.usermanagement.controller;


import com.usermanagement.responseDto.ResponseMessageDto;
import com.usermanagement.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "ESS Dashboard", description = "APIs for Employee Self-Service landing page")
@RestController
@RequestMapping("/api/ess/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @Operation(summary = "Get Employee Dashboard Summary")
    @GetMapping("/{empId}")
    public ResponseEntity<?> getDashboard(@PathVariable Long empId) {
        return ResponseEntity.ok(new ResponseMessageDto(
            HttpStatus.OK.value(),
            "Dashboard data fetched",
            dashboardService.getDashboard(empId)
        ));
    }
}
