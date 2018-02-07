package scrumweb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.nio.file.Path;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProjectDto {

    private String name;
    private String description;
    private UserProfileDto owner;
    private Path icon;
    private Set<ProjectMemberDto> members;

    public ProjectDto(String name, String description, Path icon, Set<ProjectMemberDto> members) {
        this.name = name;
        this.description = description;
        this.icon = icon;
        this.members = members;
    }
}
