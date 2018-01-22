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
    private String workflow;
    private UserProfile owner;
    private String icon;
    private Set<UserProfile> access;

}
