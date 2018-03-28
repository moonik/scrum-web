package scrumweb.common.asm;

import org.springframework.stereotype.Component;
import scrumweb.dto.user.UserDto;
import scrumweb.dto.user.UserProfileDto;
import scrumweb.user.account.domain.UserAccount;
import scrumweb.user.profile.domain.UserProfile;

@Component
public class UserProfileAsm {

    public static UserProfile makeUserProfile(UserDto userDto) {
        return new UserProfile(userDto.getUsername(), userDto.getFirstname(), userDto.getLastname(), null);
    }

    public static UserProfileDto makeUserProfileDto(UserAccount userAccount, UserProfile userProfile) {
        return new UserProfileDto(userAccount.getId(), userProfile.getFirstname(), userProfile.getLastname(), userProfile.getPhoto(), userAccount.getUsername());
    }

    public static UserProfileDto convertFromUserProfileToUserProfileDto(UserProfile userProfile){
        return new UserProfileDto(userProfile.getId(), userProfile.getFirstname(), userProfile.getLastname(), userProfile.getPhoto(), userProfile.getUsername());
    }
}
