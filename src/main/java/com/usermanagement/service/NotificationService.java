package com.usermanagement.service;

import com.usermanagement.entity.Employee;
import com.usermanagement.entity.Notification;
import java.util.List;

public interface NotificationService {
    void sendNotification(Employee recipient, String title, String message, String type);
    List<Notification> getMyNotifications(Long employeeId);
    void markAsRead(Long notificationId);

    void deleteNotification(Long id);
}
