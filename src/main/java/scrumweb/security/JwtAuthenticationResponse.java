package scrumweb.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import scrumweb.dto.user.UserInformationDto;

@AllArgsConstructor
@Getter
public class JwtAuthenticationResponse {

    private final String token;
    private final UserInformationDto userInformationDto;

}
