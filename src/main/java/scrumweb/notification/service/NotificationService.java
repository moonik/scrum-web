package scrumweb.notification.service;

import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import scrumweb.common.SecurityContextService;
import scrumweb.dto.notification.NotificationDto;
import scrumweb.notification.domain.Notification;
import scrumweb.user.account.domain.UserAccount;
import scrumweb.user.account.repository.UserAccountRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class NotificationService {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final UserAccountRepository userAccountRepository;
    private final SecurityContextService securityContextService;

    public void onMessageReceive(NotificationDto message) {
        simpMessagingTemplate.convertAndSendToUser(message.getReceiver(), "/queue/reply", message.getContent());
        UserAccount userAccount = userAccountRepository.findByUsername(message.getReceiver());
        Notification notification = new Notification(null, userAccount, message.getContent(), false);
        List<Notification> notifications = userAccount.getNotifications();
        notifications.add(notification);
        userAccount.setNotifications(notifications);
        userAccountRepository.save(userAccount);
    }

    public List<NotificationDto> getNotifications() {
        UserAccount user = securityContextService.getCurrentUserAccount();
        return convertNotifications(user.getNotifications());
    }

    public void setSeen() {
        UserAccount userAccount = securityContextService.getCurrentUserAccount();
        List<Notification> notifications = updateNotifications(userAccount.getNotifications());
        userAccount.setNotifications(notifications);
        userAccountRepository.save(userAccount);
    }

    private static List<NotificationDto> convertNotifications(List<Notification> notifications) {
        return notifications.stream()
                .map(n -> new NotificationDto(n.getReceiver().getUsername(), n.getContent()))
                .collect(Collectors.toList());
    }

    private static List<Notification> updateNotifications(List<Notification> notifications) {
        return notifications.stream()
                .peek(n -> n.setSeen(true))
                .collect(Collectors.toList());
    }
}
