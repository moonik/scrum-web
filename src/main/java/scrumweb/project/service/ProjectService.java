package scrumweb.project.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import scrumweb.common.SecurityContextService;
import scrumweb.common.asm.IssueAsm;
import scrumweb.common.asm.ProjectAsm;
import scrumweb.common.asm.UserProfileAsm;
import scrumweb.dto.issue.IssueDto;
import scrumweb.dto.issue.IssueTypeDto;
import scrumweb.dto.issue.ItemAssignee;
import scrumweb.dto.project.ProjectDetailsDto;
import scrumweb.dto.project.ProjectDto;
import scrumweb.dto.project.ProjectMemberDto;
import scrumweb.dto.search.SearchResultsDto;
import scrumweb.exception.MemberAlreadyAddedException;
import scrumweb.exception.ProjectAlreadyExsistsException;
import scrumweb.exception.ProjectMemberAccessException;
import scrumweb.exception.ProjectNotFoundException;
import scrumweb.exception.RemoveFromProjectException;
import scrumweb.issue.domain.Issue;
import scrumweb.issue.domain.IssueType;
import scrumweb.issue.repository.IssueRepository;
import scrumweb.user.account.domain.UserAccount;
import scrumweb.user.account.repository.UserAccountRepository;
import scrumweb.project.domain.Project;
import scrumweb.project.domain.ProjectMember;
import scrumweb.project.domain.ProjectMember.Role;
import scrumweb.project.repository.ProjectRepository;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProjectService {

    protected ProjectRepository projectRepository;
    protected UserAccountRepository userAccountRepository;
    protected SecurityContextService securityContextService;
    private IssueRepository issueRepository;
    private static final String[] DEFAULT_ISSUE_TYPES = {"TASK", "BUG", "FEATURE"};

    public ProjectDto create(ProjectDto projectDto) {
        if (projectRepository.findByKey(projectDto.getProjectKey()) == null) {
            Project project = ProjectAsm.makeProject(projectDto);
            UserAccount projectOwner = securityContextService.getCurrentUserAccount();
            List<Project> projects = projectOwner.getProjects();
            projects.add(createProject(project, projectOwner));
            projectOwner.setProjects(projects);
            userAccountRepository.save(projectOwner);
            return projectDto;
        } else {
            throw new ProjectAlreadyExsistsException(projectDto.getProjectKey());
        }
    }

    public void editName(String projectName, String projectKey) {
        Project project = projectRepository.findByKey(projectKey);
        if (project != null) {
            project.setName(projectName);
            projectRepository.save(project);
        } else {
            throw new ProjectNotFoundException(projectKey);
        }
    }

    public ProjectDetailsDto getProjectDetails(String projectKey) {
        final Project project = projectRepository.findByKey(projectKey);
        final ProjectDto projectDto = ProjectAsm.makeProjectDto(project);
        projectDto.setOwner(UserProfileAsm.makeUserProfileDto(project.getOwner()));
        final List<IssueDto> issues = convertIssues(project.getIssues());
        return ProjectAsm.makeProjectDetailsDro(projectDto, issues);
    }

    public List<IssueTypeDto> getIssueTypes(String key) {
        return projectRepository.findByKey(key).getIssueTypes()
                .stream()
                .map(type -> new IssueTypeDto(type.getId(), type.getName(), type.getIsDefault()))
                .collect(Collectors.toList());
    }

    public Set<ItemAssignee> getProjectMembers(String projectKey) {
        return projectRepository.findByKey(projectKey)
            .getMembers().stream()
            .map(ProjectMember::getUserAccount)
            .map(u -> new ItemAssignee(u.getId(), u.getUsername()))
            .collect(Collectors.toSet());
    }

    private static Project createProject(Project project, UserAccount projectOwner) {
        Set<ProjectMember> projectMembers = new LinkedHashSet<>();
        projectMembers.add(ProjectAsm.makeProjectMember(projectOwner, Role.PROJECT_MANAGER));
        project.setMembers(projectMembers);
        project.setIssueTypes(createIssueTypes(project));
        project.setOwner(projectOwner);
        return project;
    }

    private static Set<IssueType> createIssueTypes(Project project) {
        return Arrays.stream(DEFAULT_ISSUE_TYPES)
                .map(type -> new IssueType(type, project, true))
                .collect(Collectors.toSet());
    }

    private static List<IssueDto> convertIssues(Set<Issue> issues) {
        return issues.stream()
                .map(IssueAsm::createIssueDto)
                .sorted((i1, i2) -> Long.compare(i2.getId(), i1.getId()))
                .collect(Collectors.toList());
    }

    public SearchResultsDto findProjectsAndIssuesByKeyQuery(String param) {
        return new SearchResultsDto(getIssues(param), getProjects(param));
    }

    private List<ProjectDto> getProjects(String param) {
        return projectRepository.findProjectsByKeyQuery(param).stream()
            .map(ProjectAsm::makeProjectDto)
            .collect(Collectors.toList());
    }

    private List<IssueDto> getIssues(String param) {
        return issueRepository.findIssuesByKeyQuery(param).stream()
            .map(IssueAsm::createIssueDto)
            .collect(Collectors.toList());
    }

    public List<ProjectDto> findAllProjects() {
        return projectRepository.findProjects(securityContextService.getCurrentUserAccount()).stream()
            .map(ProjectAsm::makeProjectDto)
            .collect(Collectors.toList());
    }

    public void addMember(ProjectMemberDto projectMemberDto, String projectKey) {
        Project project = projectRepository.findByKey(projectKey);
        UserAccount memberToBeAdded = userAccountRepository.findByUsername(projectMemberDto.getUsername());
        if (findMember(project.getMembers(), memberToBeAdded.getUsername()) == null) {
            project.getMembers().add(new ProjectMember(memberToBeAdded, Role.getRole(projectMemberDto.getRole())));
            projectRepository.save(project);
        } else {
            throw new MemberAlreadyAddedException(projectMemberDto.getUsername());
        }
    }

    // todo check if member has issues before removing
    public void removeMember(String username, String projectKey) {
        Project project = projectRepository.findByKey(projectKey);
        if (!project.getOwner().getUsername().equals(username)) {
            ProjectMember projectMember = findMember(project.getMembers(), username);
            project.getMembers().remove(projectMember);
            projectRepository.save(project);
        } else {
            throw new RemoveFromProjectException(username, project.getName());
        }
    }

    public void askForAccess(ProjectMemberDto projectMemberDto, String projectKey) {
        Project project = projectRepository.findByKey(projectKey);
        UserAccount memberToBeAdded = userAccountRepository.findByUsername(projectMemberDto.getUsername());
        if (findMember(project.getRequests(), memberToBeAdded.getUsername()) == null) {
            project.getRequests().add(new ProjectMember(memberToBeAdded, Role.getRole(projectMemberDto.getRole())));
            projectRepository.save(project);
        } else {
            throw new ProjectMemberAccessException(projectMemberDto.getUsername());
        }
    }

    public void declineRequestForAccess(String projectKey, String member) {
        Project project = projectRepository.findByKey(projectKey);
        ProjectMember projectMember = findMember(project.getRequests(), member);
        project.getRequests().remove(projectMember);
        projectRepository.save(project);
    }

    private static ProjectMember findMember(Set<ProjectMember> members, String memberToBeAdded) {
        return members.stream()
                .filter(member -> member.getUserAccount().getUsername().equals(memberToBeAdded))
                .findFirst()
                .orElse(null);
    }
}