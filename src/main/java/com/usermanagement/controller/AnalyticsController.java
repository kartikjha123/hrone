package com.usermanagement.controller;

import com.usermanagement.responseDto.ResponseMessageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "Analytics & Reports", description = "HR Metrics and Visual usage charts")
@RestController
@RequestMapping("/api/admin/analytics")
public class AnalyticsController {

    @Operation(summary = "Get HR Metrics", description = "Fetch headcount, attrition rate, and salary trends.")
    @GetMapping("/summary")
    public ResponseEntity<?> getSummary() {
        Map<String, Object> metrics = Map.of(
            "totalHeadcount", 150,
            "attritionRate", "5.2%",
            "monthlySalaryCost", 2500000,
            "departmentWiseBreakup", List.of(
                Map.of("dept", "IT", "count", 40),
                Map.of("dept", "HR", "count", 10),
                Map.of("dept", "Production", "count", 100)
            )
        );
        return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "Analytics summary fetched", metrics));
    }

    @Operation(summary = "Top Performers", description = "List employees with highest performance ratings.")
    @GetMapping("/top-performers")
    public ResponseEntity<?> getTopPerformers() {
        return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "Top performers list fetched"));
    }
}
