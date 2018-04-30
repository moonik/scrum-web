package scrumweb.notification;

import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;
import scrumweb.dto.NotificationDto;

import java.security.Principal;

import static scrumweb.common.ApplicationConstants.API_URL;

@RestController(API_URL)
@AllArgsConstructor
public class NotificationController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/notification/send")
    public void onMessageReceive(Principal principal, NotificationDto message) {
        this.simpMessagingTemplate.convertAndSendToUser(message.getToUser(), "/queue/reply", message.getContent());
    }
}
