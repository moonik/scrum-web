package scrumweb.dto.project;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import scrumweb.dto.user.UserProfileDto;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProjectDto {

    private Long id;
    private String name;
    private String projectKey;
    private String description;
    private UserProfileDto owner;
    private String icon;
    private Set<ProjectMemberDto> members;
    private Set<ProjectMemberDto> requests;

    public ProjectDto(Long id, String name, String description,
                      String icon, Set<ProjectMemberDto> members, String projectKey,
                      UserProfileDto owner, Set<ProjectMemberDto> requests) {
        this.id = id;
        this.name = name;
        this.projectKey = projectKey;
        this.description = description;
        this.icon = icon;
        this.members = members;
        this.owner = owner;
        this.requests = requests;
    }

    public ProjectDto(String name, String description, String icon, String projectKey) {
        this.name = name;
        this.projectKey = projectKey;
        this.description = description;
        this.icon = icon;
    }

}
