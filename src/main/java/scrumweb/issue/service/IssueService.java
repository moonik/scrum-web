package scrumweb.issue.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scrumweb.common.SecurityContextService;
import scrumweb.common.asm.fieldcontent.FieldContentAsm;
import scrumweb.common.asm.projectfield.ProjectFieldAsm;
import scrumweb.common.asm.IssueAsm;
import scrumweb.common.asm.UserProfileAsm;
import scrumweb.dto.fieldcontent.FieldContentDto;
import scrumweb.dto.issue.IssueDetailsDto;
import scrumweb.dto.user.UserProfileDto;
import scrumweb.issue.domain.Issue;
import scrumweb.issue.domain.IssueType;
import scrumweb.issue.fieldcontent.FieldContent;
import scrumweb.issue.repository.FieldContentRepository;
import scrumweb.issue.repository.IssueRepository;
import scrumweb.issue.repository.IssueTypeRepository;
import scrumweb.project.domain.Project;
import scrumweb.projectfield.domain.ProjectField;
import scrumweb.projectfield.repository.ProjectFieldRepository;
import scrumweb.project.repository.ProjectRepository;
import scrumweb.user.account.domain.UserAccount;
import scrumweb.user.account.repository.UserAccountRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public abstract class IssueService implements FieldContentAsm<FieldContent, FieldContentDto, ProjectField> {

    @Autowired
    private IssueAsm issueAsm;
    @Autowired
    private IssueRepository issueRepository;
    @Autowired
    private UserAccountRepository userAccountRepository;
    @Autowired
    private SecurityContextService securityContextService;
    @Autowired
    private ProjectFieldRepository projectFieldRepository;
    @Autowired
    private IssueTypeRepository issueTypeRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserProfileAsm userProfileAsm;

    public IssueDetailsDto create(IssueDetailsDto issueDetailsDto, Long projectId) {
        final Project project = projectRepository.getOne(projectId);
        Set<Issue> issues = project.getIssues();
        issues.add(createIssue(issueDetailsDto));
        projectRepository.save(project);
        return issueDetailsDto;
    }

    private Issue createIssue(IssueDetailsDto issueDetailsDto) {
        final UserAccount reporter = securityContextService.getCurrentUserAccount();
        Set<UserAccount> assignees = userAccountRepository.findUsers(extractUserNames(issueDetailsDto.getAssignees()));
        Set<FieldContent> fieldContents = extractContents(issueDetailsDto.getProjectFields());
        IssueType issueType = issueTypeRepository.findByName(issueDetailsDto.getIssueType());
        Issue issue = issueAsm.createIssueEntityObject(issueDetailsDto, assignees, reporter, fieldContents, issueType);
        issueRepository.save(issue);
        return issue;
    }

    public IssueDetailsDto getIssue(Long id) {
        Issue issue = issueRepository.getOne(id);
        Set<UserProfileDto> assignees = issue.getAssignees().stream().map(userAccount -> userProfileAsm.makeUserProfileDto(userAccount, userAccount.getUserProfile())).collect(Collectors.toSet());
        UserProfileDto reporter = userProfileAsm.makeUserProfileDto(issue.getReporter(), issue.getReporter().getUserProfile());
        Set<FieldContentDto> fieldsContentsDto = issue.getFieldContents().stream().map(this::convertToDtoObject).collect(Collectors.toSet());
        return issueAsm.createIssueDetailsDto(issue, assignees, reporter, fieldsContentsDto);
    }

    private Set<String> extractUserNames(Set<UserProfileDto> userProfileDtos) {
        return userProfileDtos.stream()
                .map(UserProfileDto::getUsername)
                .collect(Collectors.toSet());
    }

    private Set<FieldContent> extractContents(Set<FieldContentDto> fieldContentsDto) {
        return fieldContentsDto.stream()
                .map(fieldContentDto -> convertToEntityObject(
                        projectFieldRepository.getOne(fieldContentDto.getProjectFieldId()), fieldContentDto))
                .collect(Collectors.toSet());
    }
}
