package scrumweb.common;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import scrumweb.user.account.domain.UserAccount;
import scrumweb.user.account.repository.UserAccountRepository;
import scrumweb.user.profile.domain.UserProfile;

@Service
@AllArgsConstructor
public class SecurityContextService {

    private UserAccountRepository userAccountRepository;

    public UserAccount getCurrentUserAccount() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userAccountRepository.findByUsername(authentication.getName());
    }
}