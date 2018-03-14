package scrumweb.project.service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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
import scrumweb.dto.search.SearchResultsDto;
import scrumweb.exception.ProjectAlreadyExsistsException;
import scrumweb.exception.ProjectNotFoundException;
import scrumweb.issue.domain.IssueType;
import scrumweb.issue.repository.IssueRepository;
import scrumweb.project.domain.Project;
import scrumweb.project.domain.ProjectMember;
import scrumweb.project.domain.ProjectMember.Role;
import scrumweb.project.repository.ProjectRepository;
import scrumweb.storage.StorageUtils;
import scrumweb.storage.service.Location;
import scrumweb.user.account.domain.UserAccount;
import scrumweb.user.account.repository.UserAccountRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
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
    private final Location location;
    private IssueRepository issueRepository;

    public ProjectDto create(ProjectDto projectDto) {
        if (projectRepository.findByKey(projectDto.getProjectKey()) == null) {
            Project project = projectAsm.makeProject(projectDto);

            UserAccount projectOwner = securityContextService.getCurrentUserAccount();
            List<Project> projects = projectOwner.getProjects();
            project.setOwner(projectOwner);

            Set<ProjectMember> projectMembers = new LinkedHashSet<>();
            projectMembers.add(projectAsm.makeProjectMember(projectOwner, Role.PROJECT_MANAGER));
            project.setMembers(projectMembers);
            project.setIssueTypes(createIssueTypes(project));

            project.setIcon(projectDto.getIcon());

            projects.add(project);
            userAccountRepository.save(projectOwner);
            return projectDto;
        } else {
            throw new ProjectAlreadyExsistsException(projectDto.getProjectKey());
        }
    }

    public void editName(String projectName, Long id) {
        Project project = projectRepository.findOne(id);
        if (project != null) {
            project.setName(projectName);
        } else {
            throw new ProjectNotFoundException(id);
        }
    }

    public ProjectDetailsDto getProjectDetails(String projectKey) {
        final Project project = projectRepository.findByKey(projectKey);
        final ProjectDto projectDto = projectAsm.makeProjectDto(project, userProfileAsm.makeUserProfileDto(project.getOwner(), project.getOwner().getUserProfile()));
        projectDto.setOwner(userProfileAsm.makeUserProfileDto(project.getOwner(), project.getOwner().getUserProfile()));
        final List<IssueDto> issues = project.getIssues().stream()
            .map(issue -> issueAsm.createIssueDto(issue))
            .sorted((i1, i2) -> Long.compare(i2.getId(), i1.getId()))
            .collect(Collectors.toList());
        return projectAsm.makeProjectDetailsDro(projectDto, issues);
    }

    public HttpStatus addMember(ProjectMemberDto projectMemberDto) {
        Project project = projectRepository.findOne(projectMemberDto.getProjectId());
        UserAccount userAccount = userAccountRepository.findByUsername(projectMemberDto.getUsername());
        if (project.getMembers().stream()
            .filter(member -> member.getUserAccount().getUsername().equals(userAccount.getUsername()))
            .collect(Collectors.toList())
            .isEmpty()) {
            project.getMembers().add(new ProjectMember(userAccount, Role.getRole(projectMemberDto.getRole())));
            projectRepository.save(project);
            return HttpStatus.OK;
        } else return HttpStatus.CONFLICT;
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

    public List<ProjectDto> getAllProjects(UserAccount userAccount) {
        List<ProjectDto> projectDtos = new ArrayList<>();
        for (Project project : projectRepository.findAll()) {
            if (!project.getMembers().stream()
                .map(ProjectMember::getUserAccount)
                .filter(member -> member.equals(userAccount))
                .collect(Collectors.toList()).isEmpty()) {
                projectDtos.add(projectAsm.convertFromProjectToProjectDto(project, userProfileAsm.makeUserProfileDto(project.getOwner(), project.getOwner().getUserProfile())));
            }
        }
        return projectDtos;
    }

    // todo check if member has issues before removing
    public HttpStatus removeMember(String username, Long id) {
        Project project = projectRepository.findOne(id);
        if (!project.getOwner().getUsername().equals(username)) {
            ProjectMember projectMember = project.getMembers().stream()
                .filter(member -> member.getUserAccount().getUsername().equals(username))
                .findFirst()
                .orElse(null);
            project.getMembers().remove(projectMember);
            projectRepository.save(project);
            return HttpStatus.OK;
        } else return HttpStatus.I_AM_A_TEAPOT;
    }

    public HttpStatus changeProjectIcon(String filename, String key) {
        if (StorageUtils.checkFile(filename, location.toPath())) {
            Project project = projectRepository.findByKey(key);
            project.setIcon(filename);
            projectRepository.save(project);
            return HttpStatus.OK;
        } else return HttpStatus.NOT_FOUND;
    }

    public SearchResultsDto findProjectsAndIssuesByKeyQuery(String param) {
        return new SearchResultsDto(getIssues(param), getProjects(param));
    }

    private List<ProjectDto> getProjects(String param) {
        return projectRepository.findProjectsByKeyQuery(param).stream()
            .map(project -> projectAsm.convertFromProjectToProjectDto(project, userProfileAsm.makeUserProfileDto(project.getOwner(), project.getOwner().getUserProfile())))
            .collect(Collectors.toList());
    }

    private List<IssueDto> getIssues(String param) {
        return issueRepository.findIssuesByKeyQuery(param).stream()
            .map(issue -> issueAsm.createIssueDto(issue))
            .collect(Collectors.toList());
    }

    public List<ProjectDto> findAllProjects() {
        return projectRepository.findAll().stream()
            .map(project -> projectAsm.convertFromProjectToProjectDto(project, userProfileAsm.makeUserProfileDto(project.getOwner(), project.getOwner().getUserProfile())))
            .collect(Collectors.toList());
    }

    public HttpStatus askForAccess(ProjectMemberDto projectMemberDto) {
        Project project = projectRepository.findOne(projectMemberDto.getProjectId());
        UserAccount userAccount = userAccountRepository.findByUsername(projectMemberDto.getUsername());
        if (project.getRequests().stream()
            .filter(request -> request.getUserAccount().getUsername().equals(userAccount.getUsername()))
            .collect(Collectors.toList())
            .isEmpty()) {
            project.getRequests().add(new ProjectMember(userAccount, Role.getRole(projectMemberDto.getRole())));
            projectRepository.save(project);
            return HttpStatus.OK;
        } else return HttpStatus.CONFLICT;
    }

    public HttpStatus declineRequestForAccess(Long id, String member) {
        Project project = projectRepository.findOne(id);
        ProjectMember projectMember = project.getRequests().stream()
            .filter(request -> request.getUserAccount().getUsername().equals(member))
            .findFirst()
            .orElse(null);
        project.getRequests().remove(projectMember);
        projectRepository.save(project);
        return HttpStatus.OK;
    }

    public HttpStatus acceptRequestForAccess(ProjectMemberDto projectMemberDto) {
        if (addMember(projectMemberDto).equals(HttpStatus.OK) &&
            declineRequestForAccess(projectMemberDto.getProjectId(), projectMemberDto.getUsername()).equals(HttpStatus.OK)) {
            return HttpStatus.OK;
        } else return HttpStatus.CONFLICT;

    }


}