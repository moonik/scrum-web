package scrumweb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import scrumweb.user.project.domain.ProjectMember;


@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class ProjectMemberDto {

    private Long projectID;
    private String username;
    private String role;

}
