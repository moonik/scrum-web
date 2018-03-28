package scrumweb.dto.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter @Setter
@Builder
public class UserProfileDto {
    private Long profileId;
    private String firstname;
    private String lastname;
    private String photo;
    private String username;
}
