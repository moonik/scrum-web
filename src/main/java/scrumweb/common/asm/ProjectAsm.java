package scrumweb.common.asm;

import org.springframework.stereotype.Component;
import scrumweb.dto.ProjectDto;
import scrumweb.user.project.domain.Project;

@Component
public class ProjectAsm {
    public Project makeProject(ProjectDto projectDto) {
        return new Project(projectDto.getName(), projectDto.getDescription(), projectDto.getIcon(), projectDto.getAccess());
    }

}
