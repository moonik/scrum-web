package scrumweb.common.asm;

import org.springframework.stereotype.Component;
import scrumweb.dto.ProjectDto;
import scrumweb.user.account.domain.UserAccount;
import scrumweb.project.domain.Project;
import scrumweb.project.domain.ProjectMember;

@Component
public class ProjectAsm {
    public Project makeProject(ProjectDto projectDto) {
        return new Project(projectDto.getName(), projectDto.getDescription(), projectDto.getIcon());
    }

    public ProjectMember makeProjectMember(UserAccount userAccount, ProjectMember.Role role){
        return  new ProjectMember(userAccount, role);
    }
}
