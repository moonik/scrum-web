package scrumweb.user.project.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scrumweb.common.SecurityContextService;
import scrumweb.common.asm.ProjectAsm;
import scrumweb.dto.ProjectDto;
import scrumweb.exception.ProjectAlreadyExsistsException;
import scrumweb.exception.ProjectNotFoundException;
import scrumweb.project.domain.Project;
import scrumweb.project.repository.ProjectRepository;
import scrumweb.user.account.domain.UserAccount;
import scrumweb.user.profile.domain.UserProfile;

@Service
@AllArgsConstructor
public class ProjectService {

    protected ProjectAsm projectAsm;
    protected ProjectRepository projectRepository;

    @Autowired
    SecurityContextService securityContextService;

    public ProjectDto create(ProjectDto projectDto){
        if (projectRepository.findByName(projectDto.getName()) == null) {
            Project project = projectAsm.makeProject(projectDto);

            UserAccount projectOwner = securityContextService.getCurrentUserAccount();
            project.setOwner(projectOwner);

            projectRepository.save(project);
            return projectDto;
        }else{
            throw new ProjectAlreadyExsistsException(projectDto.getName());
        }
    }

    public ProjectDto editName(String projectName, Long id){
        Project project = projectRepository.findOne(id);
        if(project != null){
            project.setName(projectName);
            return projectAsm.makeProjectDto(projectRepository.save(project));
        }else{
            throw new ProjectNotFoundException(id);
        }
    }
}
