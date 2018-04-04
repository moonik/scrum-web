package scrumweb.common.asm;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import scrumweb.dto.user.UserDto;
import scrumweb.dto.user.UserInformationDto;
import scrumweb.user.account.domain.UserAccount;
import scrumweb.user.profile.domain.UserProfile;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserAccountAsm {

    public static UserAccount makeUserAccount(UserDto userDto, UserProfile userProfile) {
        return UserAccount.builder()
                .username(userDto.getUsername())
                .password(new BCryptPasswordEncoder().encode(userDto.getPassword()))
                .userProfile(userProfile)
                .enabled(true)
                .build();
    }

    public static UserInformationDto makeUserInformation(UserAccount userAccount){
        return UserInformationDto.builder()
                .username(userAccount.getUsername())
                .profileId(userAccount.getUserProfile().getId())
                .build();
    }
}
