package scrumweb.user.account.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import scrumweb.common.SecurityContextService;
import scrumweb.common.asm.UserAccountAsm;
import scrumweb.common.asm.UserProfileAsm;
import scrumweb.dto.notification.NotificationDto;
import scrumweb.dto.user.UserDto;
import scrumweb.dto.user.UserInformationDto;
import scrumweb.exception.UserAlreadyExistsException;
import scrumweb.notification.domain.Notification;
import scrumweb.security.domain.Authority;
import scrumweb.security.domain.AuthorityName;
import scrumweb.security.repository.AuthorityRepository;
import scrumweb.user.account.domain.UserAccount;
import scrumweb.user.account.repository.UserAccountRepository;
import scrumweb.user.profile.domain.UserProfile;
import scrumweb.user.profile.repository.UserProfileRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserAccountService {

    protected UserAccountRepository userAccountRepository;
    protected UserProfileRepository userProfileRepository;
    protected AuthorityRepository  authorityRepository;
    protected SecurityContextService securityContextService;

    public void save(UserDto userDto) {
        if (userAccountRepository.findByUsername(userDto.getUsername()) == null) {
            UserProfile userProfile = UserProfileAsm.makeUserProfile(userDto);

            UserAccount userAccount = UserAccountAsm.makeUserAccount(userDto, userProfile);
            Authority authority = authorityRepository.findByName(AuthorityName.ROLE_USER);

            List<Authority> authorities = new ArrayList<>();
            authorities.add(authority);
            userAccount.setAuthorities(authorities);
            userAccount.setEnabled(true);

            userAccountRepository.save(userAccount);
        } else
            throw new UserAlreadyExistsException(userDto.getUsername());
    }

    public UserInformationDto getUserInformation(UserAccount userAccount){
        return UserAccountAsm.makeUserInformation(userAccount);
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
                .sorted((n1, n2) -> Boolean.compare(n1.getSeen(), n2.getSeen()))
                .map(n -> new NotificationDto(n.getReceiver().getUsername(), n.getContent(), n.getSeen()))
                .collect(Collectors.toList());
    }

    private static List<Notification> updateNotifications(List<Notification> notifications) {
        return notifications.stream()
                .peek(n -> n.setSeen(true))
                .collect(Collectors.toList());
    }
}
