package scrumweb.user.project.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scrumweb.common.asm.ProjectAsm;
import scrumweb.dto.ProjectDto;
import scrumweb.dto.ProjectMemberDto;
import scrumweb.exception.ProjectAlreadyExsistsException;
import scrumweb.user.account.repository.UserAccountRepository;
import scrumweb.user.project.domain.Project;
import scrumweb.user.project.domain.ProjectMember;
import scrumweb.user.project.repository.ProjectRepository;

@Service
@AllArgsConstructor
public class ProjectService {

    protected ProjectAsm projectAsm;
    protected ProjectRepository projectRepository;
    protected UserAccountRepository userAccountRepository;

    public ProjectDto create(ProjectDto projectDto){
        if (projectRepository.findByName(projectDto.getName()) == null) {
            Project project = projectAsm.makeProject(projectDto);
            projectRepository.save(project);
            return projectDto;
        }else{
            throw new ProjectAlreadyExsistsException(projectDto.getName());
        }
    }

    public void addMember(ProjectMemberDto projectMemberDto){
        Project project = projectRepository.getOne(projectMemberDto.getProjectID());
                project.getAccess().add(new ProjectMember(userAccountRepository.findByUsername(projectMemberDto.getUsername()).getUserProfile(),
                                                                                                ProjectMember.Role.getRole(projectMemberDto.getRole())));
                projectRepository.save(project);
    }
}
