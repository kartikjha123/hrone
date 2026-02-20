package com.usermanagement.controller;

import com.usermanagement.entity.PerformanceReview;
import com.usermanagement.entity.Employee;
import com.usermanagement.repository.PerformanceReviewRepository;
import com.usermanagement.repository.EmployeeRepository;
import com.usermanagement.responseDto.ResponseMessageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Tag(name = "Performance Management", description = "APIs for Performance reviews, Ratings and Goal setting")
@RestController
@RequestMapping("/performance")
public class PerformanceController {

    @Autowired
    private PerformanceReviewRepository performanceReviewRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Operation(summary = "Submit Performance Review", description = "Manager submits a performance review for an employee.")
    @PostMapping("/review")
    public ResponseEntity<?> submitReview(@RequestBody PerformanceReview review) {
        review.setReviewDate(LocalDate.now());
        performanceReviewRepository.save(review);
        return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "Performance review submitted successfully"));
    }

    @Operation(summary = "Get Employee Review History", description = "Fetch all previous performance reviews for an employee.")
    @GetMapping("/history/{empId}")
    public ResponseEntity<?> getHistory(@PathVariable Long empId) {
        return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "Fetched review history", 
                performanceReviewRepository.findByEmployeeId(empId)));
    }

    @Operation(summary = "Update Performance Review", description = "Updates an existing performance review")
    @PutMapping("/review/{id}")
    public ResponseEntity<?> updateReview(@PathVariable Long id, @RequestBody PerformanceReview review) {
        PerformanceReview existingReview = performanceReviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Performance review not found with id: " + id));
        existingReview.setRating(review.getRating());
        existingReview.setComments(review.getComments());
        existingReview.setGoals(review.getGoals());
        performanceReviewRepository.save(existingReview);
        return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "Performance review updated successfully"));
    }

    @Operation(summary = "Delete Performance Review", description = "Deletes a performance review")
    @DeleteMapping("/review/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable Long id) {
        if (!performanceReviewRepository.existsById(id)) {
            throw new RuntimeException("Performance review not found with id: " + id);
        }
        performanceReviewRepository.deleteById(id);
        return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "Performance review deleted successfully"));
    }
}
