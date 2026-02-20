package com.usermanagement.controller;

import com.usermanagement.responseDto.ResponseMessageDto;
import com.usermanagement.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Notifications", description = "APIs for In-app notifications and alerts")
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Operation(summary = "Get My Notifications", description = "Fetch all notifications for the logged-in employee.")
    @GetMapping("/{empId}")
    public ResponseEntity<?> getMyNotifications(@PathVariable Long empId) {
        return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "Fetched notifications", 
                notificationService.getMyNotifications(empId)));
    }

    @Operation(summary = "Mark Notification as Read", description = "Update the status of a notification to read.")
    @PutMapping("/{id}/read")
    public ResponseEntity<?> markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
        return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "Notification marked as read"));
    }

    @Operation(summary = "Delete Notification", description = "Deletes a notification.")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "Notification deleted successfully"));
    }
}
