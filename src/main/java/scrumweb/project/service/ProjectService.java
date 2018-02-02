package scrumweb.project.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import scrumweb.common.SecurityContextService;
import scrumweb.common.asm.ProjectAsm;
import scrumweb.dto.project.ProjectDto;
import scrumweb.dto.project.ProjectMemberDto;
import scrumweb.exception.ProjectAlreadyExsistsException;
import scrumweb.exception.ProjectNotFoundException;
import scrumweb.user.account.domain.UserAccount;
import scrumweb.user.account.repository.UserAccountRepository;
import scrumweb.project.domain.Project;
import scrumweb.project.domain.ProjectMember;
import scrumweb.project.domain.ProjectMember.Role;
import scrumweb.project.repository.ProjectRepository;

import java.util.LinkedHashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class ProjectService {

    protected ProjectAsm projectAsm;
    protected ProjectRepository projectRepository;
    protected UserAccountRepository userAccountRepository;
    protected SecurityContextService securityContextService;

    public ProjectDto create(ProjectDto projectDto){
        if (projectRepository.findByName(projectDto.getName()) == null) {
            Project project = projectAsm.makeProject(projectDto);

            UserAccount projectOwner = securityContextService.getCurrentUserAccount();
            project.setOwner(projectOwner);
            //TO DO add issue types here and fields
            //attach them to project
            Set<ProjectMember> projectMembers = new LinkedHashSet<>();
            projectMembers.add(projectAsm.makeProjectMember(projectOwner,Role.PROJECT_MANAGER));
            project.setMembers(projectMembers);

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

    public ProjectMemberDto addMember(ProjectMemberDto projectMemberDto){
        Project project = projectRepository.getOne(projectMemberDto.getProjectId());
        UserAccount userAccount = userAccountRepository.findByUsername(projectMemberDto.getUsername());
        project.getMembers().add(new ProjectMember(userAccount, Role.getRole(projectMemberDto.getRole())));
        projectRepository.save(project);
        return projectMemberDto;
    }
}