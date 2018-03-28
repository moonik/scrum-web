package scrumweb.dto.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter @Setter
@Builder
public class UserInformationDto {
    private String username;
    private Long profileId;
}
