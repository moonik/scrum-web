package scrumweb.notification.controller;

import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;
import scrumweb.dto.notification.NotificationDto;
import scrumweb.notification.service.NotificationService;

import static scrumweb.common.ApplicationConstants.API_URL;

@RestController(API_URL)
@AllArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @MessageMapping("/notification/send")
    public void onMessageReceive(NotificationDto message) {
        notificationService.onMessageReceive(message);
    }
}
