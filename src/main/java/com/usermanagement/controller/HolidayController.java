package com.usermanagement.controller;


import com.usermanagement.requestDto.HolidayRequestDto;
import com.usermanagement.responseDto.ResponseMessageDto;
import com.usermanagement.service.HolidayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Holiday Master", description = "APIs for Holiday Management")
@RestController
@RequestMapping("/api/admin/holidays")
public class HolidayController {

    private final HolidayService holidayService;

    public HolidayController(HolidayService holidayService) {
        this.holidayService = holidayService;
    }

    @Operation(summary = "Add new holiday")
    @PostMapping
    public ResponseEntity<?> addHoliday(@Valid @RequestBody HolidayRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            new ResponseMessageDto(
                HttpStatus.CREATED.value(),
                "Holiday added successfully",
                holidayService.addHoliday(dto)
            )
        );
    }

    @Operation(summary = "Update holiday by ID")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateHoliday(
            @PathVariable Long id,
            @Valid @RequestBody HolidayRequestDto dto) {
        return ResponseEntity.ok(
            new ResponseMessageDto(
                HttpStatus.OK.value(),
                "Holiday updated successfully",
                holidayService.updateHoliday(id, dto)
            )
        );
    }

    @Operation(summary = "Delete holiday by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHoliday(@PathVariable Long id) {
        holidayService.deleteHoliday(id);
        return ResponseEntity.ok(
            new ResponseMessageDto(
                HttpStatus.OK.value(),
                "Holiday deleted successfully",
                null
            )
        );
    }

    @Operation(summary = "Get holiday by ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(
            new ResponseMessageDto(
                HttpStatus.OK.value(),
                "Holiday fetched successfully",
                holidayService.getById(id)
            )
        );
    }

    @Operation(summary = "Get all holidays — filter by year, type, state")
    @GetMapping
    public ResponseEntity<?> getAllHolidays(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String state) {
        return ResponseEntity.ok(
            new ResponseMessageDto(
                HttpStatus.OK.value(),
                "Holidays fetched successfully",
                holidayService.getAllHolidays(year, type, state)
            )
        );
    }

    @Operation(summary = "Get upcoming holidays from today")
    @GetMapping("/upcoming")
    public ResponseEntity<?> getUpcomingHolidays() {
        return ResponseEntity.ok(
            new ResponseMessageDto(
                HttpStatus.OK.value(),
                "Upcoming holidays fetched",
                holidayService.getUpcomingHolidays()
            )
        );
    }
    
    @Operation(summary = "Get current month's holidays for calendar")
    @GetMapping("/calendar/current-month")
    public ResponseEntity<?> getCurrentMonthHolidays() {
        return ResponseEntity.ok(
            new ResponseMessageDto(
                HttpStatus.OK.value(),
                "Current month holidays fetched",
                holidayService.getCurrentMonthHolidays()
            )
        );
    }
}