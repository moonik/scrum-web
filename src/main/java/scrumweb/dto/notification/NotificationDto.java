package scrumweb.dto.notification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class NotificationDto {
    private String receiver;
    private String content;
    private Boolean seen;
}
