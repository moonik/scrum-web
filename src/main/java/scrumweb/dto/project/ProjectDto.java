package scrumweb.dto.project;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import scrumweb.dto.user.UserProfileDto;
import scrumweb.user.account.domain.UserAccount;

import java.nio.file.Path;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProjectDto {

    private String name;
    private String projectKey;
    private String description;
    private UserProfileDto owner;
    private String icon;
    private Set<ProjectMemberDto> members;


    public ProjectDto(String name, String description, String icon, Set<ProjectMemberDto> members, String projectKey) {
        this.name = name;
        this.projectKey = projectKey;
        this.description = description;
        this.icon = icon;
        this.members = members;
    }

    public ProjectDto(String name, String description, String icon, String projectKey){
        this.name = name;
        this.projectKey = projectKey;
        this.description = description;
        this.icon = icon;
    }
}
