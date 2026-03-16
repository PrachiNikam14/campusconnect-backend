package campusconnect.backend.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public List<NotificationDTO> getNotifications(Authentication authentication){

        return notificationService.getUserNotifications(
                authentication.getName()
        );
    }

    @GetMapping("/unread-count")
    public long getUnreadCount(Authentication authentication){

        return notificationService.getUnreadCount(
                authentication.getName()
        );
    }

    @PutMapping("/{id}/read")
    public void markAsRead(@PathVariable Long id){
        notificationService.markAsRead(id);
    }
}