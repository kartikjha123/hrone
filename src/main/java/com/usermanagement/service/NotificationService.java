package com.usermanagement.service;

import java.util.List;

import com.usermanagement.entity.Employee;
import com.usermanagement.responseDto.NotificationResponseDto;

public interface NotificationService {
    void sendNotification(Employee recipient, String title, String message, String type);
    List<NotificationResponseDto> getMyNotifications(Long employeeId); // ✅ DTO return

    void markAsRead(Long notificationId);

    void deleteNotification(Long id);
}
