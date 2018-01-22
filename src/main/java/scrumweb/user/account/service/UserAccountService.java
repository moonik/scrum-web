package scrumweb.user.account.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scrumweb.common.asm.UserAccountAsm;
import scrumweb.common.asm.UserProfileAsm;
import scrumweb.dto.UserDto;
import scrumweb.dto.UserInformationDto;
import scrumweb.exception.UserAlreadyExistsException;
import scrumweb.security.model.Authority;
import scrumweb.security.model.AuthorityName;
import scrumweb.security.repository.AuthorityRepository;
import scrumweb.user.account.domain.UserAccount;
import scrumweb.user.account.repository.UserAccountRepository;
import scrumweb.user.profile.domain.UserProfile;
import scrumweb.user.profile.repository.UserProfileRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserAccountService {

    protected UserAccountAsm userAccountAsm;
    protected UserProfileAsm userProfileAsm;
    protected UserAccountRepository userAccountRepository;
    protected UserProfileRepository userProfileRepository;
    protected AuthorityRepository  authorityRepository;

    public void save(UserDto userDto) {
        if (userAccountRepository.findByUsername(userDto.getUsername()) == null) {
            UserProfile userProfile = userProfileAsm.makeUserProfile(userDto);
            userProfileRepository.save(userProfile);

            UserAccount userAccount = userAccountAsm.makeUserAccount(userDto, userProfile);
            Authority authority = authorityRepository.findByName(AuthorityName.ROLE_USER);
            List<Authority> authorities = new ArrayList<>();
            authorities.add(authority);
            userAccount.setAuthorities(authorities);
            userAccount.setEnabled(true);
            userAccountRepository.save(userAccount);
        }else
            throw new UserAlreadyExistsException(userDto.getUsername());
    }

    public UserInformationDto getUserInformation(UserAccount userAccount){
        return userAccountAsm.makeUserInformation(userAccount);
    }
}
