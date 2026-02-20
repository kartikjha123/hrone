package com.usermanagement.serviceImpl;

import com.usermanagement.entity.Employee;
import com.usermanagement.entity.Notification;
import com.usermanagement.repository.NotificationRepository;
import com.usermanagement.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public void sendNotification(Employee recipient, String title, String message, String type) {
        Notification notif = new Notification();
        notif.setRecipient(recipient);
        notif.setTitle(title);
        notif.setMessage(message);
        notif.setType(type);
        notificationRepository.save(notif);

        // Real-time WebSocket push to specific user
        messagingTemplate.convertAndSendToUser(
            recipient.getId().toString(),
            "/queue/notifications",
            notif
        );
    }

    @Override
    public List<Notification> getMyNotifications(Long employeeId) {
        return notificationRepository.findByRecipient_IdOrderByCreatedAtDesc(employeeId);
    }

    @Override
    public void markAsRead(Long notificationId) {
        notificationRepository.findById(notificationId).ifPresent(n -> {
            n.setRead(true);
            notificationRepository.save(n);
        });
    }

    @Override
    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }
}
