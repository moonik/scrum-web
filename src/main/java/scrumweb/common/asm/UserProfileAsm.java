package scrumweb.common.asm;

import org.springframework.stereotype.Component;
import scrumweb.dto.user.UserDto;
import scrumweb.dto.user.UserProfileDto;
import scrumweb.user.account.domain.UserAccount;
import scrumweb.user.profile.domain.UserProfile;

@Component
public class UserProfileAsm {

    public static UserProfile makeUserProfile(UserDto userDto) {
        return UserProfile.builder()
                .username(userDto.getUsername())
                .firstname(userDto.getFirstname())
                .lastname(userDto.getLastname())
                .photo(userDto.getPhoto())
                .build();
    }

    public static UserProfileDto makeUserProfileDto(UserAccount userAccount) {
        return UserProfileDto.builder()
                .profileId(userAccount.getId())
                .firstname(userAccount.getUserProfile().getFirstname())
                .lastname(userAccount.getUserProfile().getLastname())
                .username(userAccount.getUsername())
                .photo(userAccount.getUserProfile().getPhoto())
                .build();
    }

    public static UserProfileDto convertFromUserProfileToUserProfileDto(UserProfile userProfile) {
        return UserProfileDto.builder()
                .profileId(userProfile.getId())
                .firstname(userProfile.getFirstname())
                .lastname(userProfile.getLastname())
                .photo(userProfile.getPhoto())
                .username(userProfile.getUsername())
                .build();
    }
}
