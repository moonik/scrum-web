package scrumweb.user.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scrumweb.common.asm.ProjectAsm;
import scrumweb.dto.ProjectDto;
import scrumweb.exception.ProjectAlreadyExsistsException;
import scrumweb.user.project.domain.Project;
import scrumweb.user.project.repository.ProjectRepository;

@Service
public class ProjectService {

    @Autowired
    protected ProjectAsm projectAsm;

    @Autowired
    protected ProjectRepository projectRepository;

    public void create(ProjectDto projectDto){
        if (projectRepository.findByName(projectDto.getName()) == null) {
            Project project = projectAsm.makeProject(projectDto);
            projectRepository.save(project);
        }else{
            throw new ProjectAlreadyExsistsException(projectDto.getName());
        }
    }
}
