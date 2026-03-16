package campusconnect.backend.notification;

import campusconnect.backend.entity.User;
import campusconnect.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public void createNotification(
            User user,
            String message,
            NotificationType type
    ){

        Notification notification = Notification.builder()
                .message(message)
                .type(type)
                .isRead(false)
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();

        notificationRepository.save(notification);

        NotificationDTO dto = NotificationDTO.builder()
                .id(notification.getId())
                .message(notification.getMessage())
                .type(notification.getType())
                .isRead(false)
                .createdAt(notification.getCreatedAt())
                .build();

        messagingTemplate.convertAndSend(
                "/topic/notifications/" + user.getId(),
                dto
        );

        long unreadCount =
                notificationRepository.countByUserAndIsReadFalse(user);

        messagingTemplate.convertAndSend(
                "/topic/unread/" + user.getId(),
                unreadCount
        );
    }
    public List<NotificationDTO> getUserNotifications(String email){

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return notificationRepository
                .findByUserOrderByCreatedAtDesc(user)
                .stream()
                .map(n -> NotificationDTO.builder()
                        .id(n.getId())
                        .message(n.getMessage())
                        .type(n.getType())
                        .isRead(n.isRead())
                        .createdAt(n.getCreatedAt())
                        .build())
                .toList();
    }
    public long getUnreadCount(String email){

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return notificationRepository.countByUserAndIsReadFalse(user);
    }

    public void markAsRead(Long notificationId){

        Notification notification = notificationRepository
                .findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        notification.setRead(true);

        notificationRepository.save(notification);
    }
}