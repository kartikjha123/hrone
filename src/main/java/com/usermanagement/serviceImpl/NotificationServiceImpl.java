package com.usermanagement.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.usermanagement.entity.Employee;
import com.usermanagement.entity.Notification;
import com.usermanagement.repository.NotificationRepository;
import com.usermanagement.responseDto.NotificationResponseDto;
import com.usermanagement.service.NotificationService;

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
    public List<NotificationResponseDto> getMyNotifications(Long employeeId) {

        List<Notification> notifications = notificationRepository
            .findByRecipient_IdOrderByCreatedAtDesc(employeeId);

        return notifications.stream().map(n -> {

            NotificationResponseDto dto = new NotificationResponseDto();
            dto.setId(n.getId());
            dto.setTitle(n.getTitle());
            dto.setMessage(n.getMessage());
            dto.setType(n.getType());
            dto.setRead(n.isRead());
            dto.setCreatedAt(n.getCreatedAt());

            // ✅ Employee se sirf basic info lo - no infinite loop!
            if (n.getRecipient() != null) {
                dto.setRecipientId(n.getRecipient().getId());
                dto.setRecipientName(
                    n.getRecipient().getFirstName() + " " +
                    n.getRecipient().getLastName()
                );
                // Email User se lo
                if (n.getRecipient().getUser() != null) {
                    dto.setRecipientEmail(
                        n.getRecipient().getUser().getEmail()
                    );
                }
            }

            return dto;

        }).collect(Collectors.toList());
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
