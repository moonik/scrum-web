package scrumweb.project.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import scrumweb.common.SecurityContextService;
import scrumweb.common.asm.IssueAsm;
import scrumweb.common.asm.ProjectAsm;
import scrumweb.common.asm.UserProfileAsm;
import scrumweb.dto.issue.IssueDto;
import scrumweb.dto.issue.ItemAssignee;
import scrumweb.dto.project.ProjectDetailsDto;
import scrumweb.dto.project.ProjectDto;
import scrumweb.dto.project.ProjectMemberDto;
import scrumweb.exception.ProjectAlreadyExsistsException;
import scrumweb.exception.ProjectNotFoundException;
import scrumweb.issue.domain.IssueType;
import scrumweb.user.account.domain.UserAccount;
import scrumweb.user.account.repository.UserAccountRepository;
import scrumweb.project.domain.Project;
import scrumweb.project.domain.ProjectMember;
import scrumweb.project.domain.ProjectMember.Role;
import scrumweb.project.repository.ProjectRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProjectService {

    protected ProjectAsm projectAsm;
    protected ProjectRepository projectRepository;
    protected UserAccountRepository userAccountRepository;
    protected SecurityContextService securityContextService;
    private IssueAsm issueAsm;
    private UserProfileAsm userProfileAsm;
    private static final String[] DEFAULT_ISSUE_TYPES = {"TASK", "BUG", "FEATURE"};

    public ProjectDto create(ProjectDto projectDto){
        if (projectRepository.findByKey(projectDto.getProjectKey()) == null) {
            Project project = projectAsm.makeProject(projectDto);

            UserAccount projectOwner = securityContextService.getCurrentUserAccount();
            List<Project> projects = projectOwner.getProjects();
            project.setOwner(projectOwner);

            Set<ProjectMember> projectMembers = new LinkedHashSet<>();
            projectMembers.add(projectAsm.makeProjectMember(projectOwner,Role.PROJECT_MANAGER));
            project.setMembers(projectMembers);
            project.setIssueTypes(createIssueTypes(project));

            projects.add(project);
            userAccountRepository.save(projectOwner);
            return projectDto;
        } else {
            throw new ProjectAlreadyExsistsException(projectDto.getProjectKey());
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

    public ProjectDetailsDto getProjectDetails(String projectKey) {
        final Project project = projectRepository.findByKey(projectKey);
        final ProjectDto projectDto = projectAsm.makeProjectDto(project);
        projectDto.setOwner(userProfileAsm.makeUserProfileDto(project.getOwner(), project.getOwner().getUserProfile()));
        final Set<IssueDto> issues = project.getIssues().stream().map(issue -> issueAsm.createIssueDto(issue)).collect(Collectors.toSet());
        return projectAsm.makeProjectDetailsDro(projectDto, issues);
    }

    public ProjectMemberDto addMember(ProjectMemberDto projectMemberDto){
        Project project = projectRepository.findOne(projectMemberDto.getProjectId());
        UserAccount userAccount = userAccountRepository.findByUsername(projectMemberDto.getUsername());
        project.getMembers().add(new ProjectMember(userAccount, Role.getRole(projectMemberDto.getRole())));
        projectRepository.save(project);
        return projectMemberDto;
    }

    public Set<ItemAssignee> getProjectMembers(String projectKey) {
        return projectRepository.findByKey(projectKey)
                .getMembers().stream()
                .map(ProjectMember::getUserAccount)
                .map(u -> new ItemAssignee(u.getId(), u.getUsername()))
                .collect(Collectors.toSet());
    }

    private Set<IssueType> createIssueTypes(Project project) {
        return Arrays.stream(DEFAULT_ISSUE_TYPES)
                .map(type -> new IssueType(type, project))
                .collect(Collectors.toSet());
    }

    public List<ProjectDto> getAllProjects(UserAccount userAccount){

        return userAccount.getProjects().stream()
                .map(project -> projectAsm.convertFromProjectToProjectDto(project))
                        .collect(Collectors.toList());
    }

    public void removeMember(String username, Long projectId) {
        Project project = projectRepository.findOne(projectId);
        ProjectMember projectMember = project.getMembers().stream()
            .filter(member -> member.getUserAccount().getUsername().equals(username))
            .findFirst()
            .orElse(null);
        project.getMembers().remove(projectMember);
        projectRepository.save(project);
    }
}