package scrumweb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import scrumweb.user.profile.domain.UserProfile;

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
    private Set<UserProfile> access;

    public ProjectDto(String name, String description, String icon, Set<UserProfile> access) {
        this.name = name;
        this.description = description;
        this.icon = icon;
        this.access = access;
    }
}
