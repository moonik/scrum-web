package scrumweb.notification.service;

import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import scrumweb.common.SecurityContextService;
import scrumweb.dto.notification.NotificationDto;
import scrumweb.notification.domain.Notification;
import scrumweb.notification.repository.NotificationRepository;
import scrumweb.user.account.domain.UserAccount;
import scrumweb.user.account.repository.UserAccountRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class NotificationService {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final UserAccountRepository userAccountRepository;
    private final NotificationRepository notificationRepository;

    public void onMessageReceive(NotificationDto message) {
        simpMessagingTemplate.convertAndSendToUser(message.getReceiver(), "/queue/reply", message.getContent());
        UserAccount receiver = userAccountRepository.findByUsername(message.getReceiver());
        Notification notification = new Notification(null, receiver, message.getContent(), false);
        notificationRepository.save(notification);
    }
}
