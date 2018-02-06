package scrumweb.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class UserProfileDto {

    private Long profileId;
    private String firstname;
    private String lastname;
    private String photo;
    private String username;
}
