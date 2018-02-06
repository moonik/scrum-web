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

    private String name;
    private String description;
    private UserProfileDto owner;
    private String icon;
    private Set<ProjectMemberDto> members;

    public ProjectDto(String name, String description, String icon, Set<ProjectMemberDto> members) {
        this.name = name;
        this.description = description;
        this.icon = icon;
        this.members = members;
    }
}
