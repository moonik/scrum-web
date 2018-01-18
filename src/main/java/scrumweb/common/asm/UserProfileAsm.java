package scrumweb.common.asm;

import org.springframework.stereotype.Component;
import scrumweb.dto.UserDto;
import scrumweb.user.profile.domain.UserProfile;

@Component
public class UserProfileAsm {

    public UserProfile makeUserProfile(UserDto userDto) {
        return new UserProfile(userDto.getFirstname(), userDto.getLastname(), null);
    }
}
