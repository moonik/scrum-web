package scrumweb.dto.project;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import scrumweb.dto.user.UserProfileDto;

import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter @Setter
public class ProjectDto {
    private Long id;
    private String name;
    private String projectKey;
    private String description;
    private UserProfileDto owner;
    private String icon;
    private Set<ProjectMemberDto> members;
    private Set<ProjectMemberDto> requests;
}
