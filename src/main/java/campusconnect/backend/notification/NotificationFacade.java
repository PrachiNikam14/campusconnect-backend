package campusconnect.backend.notification;

import campusconnect.backend.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationFacade {

    private final NotificationService notificationService;
    private final EmailService emailService;

    public void notifyUser(
            User user,
            String message,
            NotificationType type,
            boolean sendEmail
    ){

        // Save in-app notification
        notificationService.createNotification(
                user,
                message,
                type
        );

        // Send email if needed
        if(sendEmail){
            try {
                emailService.sendHtmlEmail(
                        user.getEmail(),
                        "CampusConnect Notification",
                        "<p>" + message + "</p>"
                );
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}