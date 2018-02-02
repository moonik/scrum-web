package scrumweb.common.asm;

import org.springframework.stereotype.Component;
import scrumweb.dto.user.UserDto;
import scrumweb.dto.user.UserProfileDto;
import scrumweb.user.account.domain.UserAccount;
import scrumweb.user.profile.domain.UserProfile;

@Component
public class UserProfileAsm {

    public UserProfile makeUserProfile(UserDto userDto) {
        return new UserProfile(userDto.getFirstname(), userDto.getLastname(), null);
    }

    public UserProfileDto makeUserProfileDto(UserAccount userAccount, UserProfile userProfile) {
        return new UserProfileDto(userAccount.getId(), userProfile.getFirstname(), userProfile.getLastname(), userProfile.getPhoto(), userAccount.getUsername());
    }
}
