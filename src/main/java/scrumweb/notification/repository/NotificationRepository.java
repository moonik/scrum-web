package scrumweb.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import scrumweb.notification.domain.Notification;

@Repository
public interface NotificationRepository  extends JpaRepository<Notification, Long> {
}
