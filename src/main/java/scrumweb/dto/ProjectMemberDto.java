package scrumweb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class ProjectMemberDto {

    private Long projectId;
    private String username;
    private String role;
    private String photo;

    public ProjectMemberDto(Long projectId, String username, String role) {
        this.projectId = projectId;
        this.username = username;
        this.role = role;
    }
}
