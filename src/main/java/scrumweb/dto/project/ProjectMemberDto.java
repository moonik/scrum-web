package scrumweb.dto.project;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
@Getter @Setter
public class ProjectMemberDto {
    private String projectKey;
    private String username;
    private String role;
    private String photo;
}
