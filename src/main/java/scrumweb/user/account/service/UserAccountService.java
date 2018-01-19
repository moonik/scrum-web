package scrumweb.user.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scrumweb.common.asm.UserAccountAsm;
import scrumweb.common.asm.UserProfileAsm;
import scrumweb.dto.UserDto;
import scrumweb.dto.UserInformationDto;
import scrumweb.exception.UserAlreadyExistsException;
import scrumweb.security.model.Authority;
import scrumweb.security.model.AuthorityName;
import scrumweb.user.account.domain.UserAccount;
import scrumweb.user.account.repository.UserAccountRepository;
import scrumweb.user.profile.domain.UserProfile;
import scrumweb.user.profile.repository.UserProfileRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserAccountService {

    @Autowired
    protected UserAccountAsm userAccountAsm;

    @Autowired
    protected UserProfileAsm userProfileAsm;

    @Autowired
    protected UserAccountRepository userAccountRepository;

    @Autowired
    protected UserProfileRepository userProfileRepository;

    public void save(UserDto userDto) {
        if (userAccountRepository.findByUsername(userDto.getUsername()) == null) {
            UserProfile userProfile = userProfileAsm.makeUserProfile(userDto);
            userProfileRepository.save(userProfile);

            UserAccount userAccount = userAccountAsm.makeUserAccount(userDto, userProfile);
            Authority authority = new Authority();
            authority.setName(AuthorityName.ROLE_USER);
            List<Authority> authorities = new ArrayList<>();
            authorities.add(authority);
            userAccount.setAuthorities(authorities);
            userAccountRepository.save(userAccount);
        }else
            throw new UserAlreadyExistsException(userDto.getUsername());
    }

    public UserInformationDto getUserInformation(UserAccount userAccount){
        return userAccountAsm.makeUserInformation(userAccount);
    }
}
