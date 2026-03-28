package com.usermanagement.controller;



import com.usermanagement.responseDto.BirthdayResponseDto;
import com.usermanagement.responseDto.ResponseMessageDto;
import com.usermanagement.service.BirthdayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Birthday", description = "APIs for Today's and Upcoming Birthdays")
@RestController
@RequestMapping("/api/birthdays")
public class BirthdayController {

    private final BirthdayService birthdayService;

    public BirthdayController(BirthdayService birthdayService) {
        this.birthdayService = birthdayService;
    }

    @Operation(summary = "Today's Birthdays",
               description = "Aaj jinki birthday hai unki list")
    @GetMapping("/today")
    public ResponseEntity<?> getTodayBirthdays() {
        List<BirthdayResponseDto> list = birthdayService.getTodayBirthdays();
        String message = list.isEmpty()
                ? "No birthdays today"
                : list.size() + " birthday(s) today!";
        return ResponseEntity.ok(
            new ResponseMessageDto(HttpStatus.OK.value(), message, list)
        );
    }

    @Operation(summary = "Upcoming Birthdays this month",
               description = "Is month ke baaki upcoming birthdays")
    @GetMapping("/upcoming")
    public ResponseEntity<?> getUpcomingBirthdays() {
        List<BirthdayResponseDto> list = birthdayService.getUpcomingBirthdaysThisMonth();
        String message = list.isEmpty()
                ? "No upcoming birthdays this month"
                : list.size() + " upcoming birthday(s) this month";
        return ResponseEntity.ok(
            new ResponseMessageDto(HttpStatus.OK.value(), message, list)
        );
    }
}
