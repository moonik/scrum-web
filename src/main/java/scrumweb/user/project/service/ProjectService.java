package scrumweb.user.project.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import scrumweb.common.SecurityContextService;
import scrumweb.common.asm.ProjectAsm;
import scrumweb.dto.ProjectDto;
import scrumweb.dto.ProjectMemberDto;
import scrumweb.exception.ProjectAlreadyExsistsException;
import scrumweb.user.account.domain.UserAccount;
import scrumweb.user.account.repository.UserAccountRepository;
import scrumweb.user.profile.domain.UserProfile;
import scrumweb.user.project.domain.Project;
import scrumweb.user.project.domain.ProjectMember;
import scrumweb.user.project.repository.ProjectRepository;

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

            projectRepository.save(project);
            return projectDto;
        }else{
            throw new ProjectAlreadyExsistsException(projectDto.getName());
        }
    }

    public void addMember(ProjectMemberDto projectMemberDto){
        Project project = projectRepository.getOne(projectMemberDto.getProjectID());
                project.getMembers().add(new ProjectMember(userAccountRepository.findByUsername(projectMemberDto.getUsername()).getUserProfile(),
                                                                                                ProjectMember.Role.getRole(projectMemberDto.getRole())));
                projectRepository.save(project);
    }
}
