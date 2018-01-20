package scrumweb.user.account.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scrumweb.common.asm.UserAccountAsm;
import scrumweb.common.asm.UserProfileAsm;
import scrumweb.dto.UserDto;
import scrumweb.exception.UserAlreadyExistsException;
import scrumweb.user.account.domain.UserAccount;
import scrumweb.user.account.repository.UserAccountRepository;
import scrumweb.user.profile.domain.UserProfile;
import scrumweb.user.profile.repository.UserProfileRepository;

@Service
@AllArgsConstructor
public class UserAccountService {

    protected UserAccountAsm userAccountAsm;
    protected UserProfileAsm userProfileAsm;
    protected UserAccountRepository userAccountRepository;
    protected UserProfileRepository userProfileRepository;

    public void save(UserDto userDto) {
        if (userAccountRepository.findByUsername(userDto.getUsername()) == null) {
            UserProfile userProfile = userProfileAsm.makeUserProfile(userDto);
            userProfileRepository.save(userProfile);

            UserAccount userAccount = userAccountAsm.makeUserAccount(userDto, userProfile);
            userAccountRepository.save(userAccount);
        }else
            throw new UserAlreadyExistsException(userDto.getUsername());
    }
}
