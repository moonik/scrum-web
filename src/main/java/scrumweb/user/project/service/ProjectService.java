package scrumweb.user.project.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scrumweb.common.asm.ProjectAsm;
import scrumweb.dto.ProjectDto;
import scrumweb.exception.ProjectAlreadyExsistsException;
import scrumweb.user.project.domain.Project;
import scrumweb.user.project.repository.ProjectRepository;

@Service
@AllArgsConstructor
public class ProjectService {

    protected ProjectAsm projectAsm;
    protected ProjectRepository projectRepository;

    public ProjectDto create(ProjectDto projectDto){
        if (projectRepository.findByName(projectDto.getName()) == null) {
            Project project = projectAsm.makeProject(projectDto);
            projectRepository.save(project);
            return projectDto;
        }else{
            throw new ProjectAlreadyExsistsException(projectDto.getName());
        }
    }
}
