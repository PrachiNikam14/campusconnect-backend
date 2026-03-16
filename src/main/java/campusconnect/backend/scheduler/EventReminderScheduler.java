package campusconnect.backend.scheduler;

import campusconnect.backend.entity.EventRequest;
import campusconnect.backend.repository.EventRequestRepository;
import campusconnect.backend.notification.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EventReminderScheduler {

    private final EventRequestRepository eventRequestRepository;
    private final EmailService emailService;

    @Scheduled(cron = "0 0 9 * * ?")
    public void sendEventReminders() {

        LocalDateTime tomorrowStart = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0);
        LocalDateTime tomorrowEnd = tomorrowStart.plusHours(23).plusMinutes(59);

        List<EventRequest> events =
                eventRequestRepository.findByEventDateBetween(
                        tomorrowStart,
                        tomorrowEnd
                );




        for (EventRequest event : events) {

            String email = event.getCollege().getUser().getEmail();
            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm");
            String formattedDate =
                    event.getEventDate().format(formatter);

            String html = """
<html>
<body style="font-family:Arial;background:#f4f6f8;padding:30px">

<table width="100%" style="max-width:600px;margin:auto;background:white;padding:30px;border-radius:8px">

<tr>
<td align="center">
<h2 style="color:#4CAF50;">CampusConnect</h2>
</td>
</tr>

<tr>
<td>

<p>Hello,</p>

<p>This is a reminder that your event is tomorrow.</p>

<p>
<b>Event:</b> %s<br>
<b>Date:</b> %s<br>
<b>College:</b> %s
</p>

<p>Please make sure all arrangements are ready.</p>

</td>
</tr>

</table>

</body>
</html>
"""


            .formatted(
                    event.getTitle(),
                    formattedDate,
                    event.getCollege().getName()
            );

            try {
                emailService.sendHtmlEmail(
                        email,
                        "Event Reminder - CampusConnect",
                        html
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}