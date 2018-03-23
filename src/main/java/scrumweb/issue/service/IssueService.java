package scrumweb.issue.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import scrumweb.common.SecurityContextService;
import scrumweb.common.asm.IssueAsm;
import scrumweb.common.asm.UserProfileAsm;
import scrumweb.common.asm.fieldcontent.FieldContentConverter;
import scrumweb.dto.fieldcontent.FieldContentDto;
import scrumweb.dto.issue.IssueDetailsDto;
import scrumweb.dto.user.UserProfileDto;
import scrumweb.exception.CantAssignToIssueException;
import scrumweb.issue.domain.Issue;
import scrumweb.issue.domain.IssueType;
import scrumweb.issue.fieldcontent.FieldContent;
import scrumweb.issue.repository.IssueRepository;
import scrumweb.project.domain.Project;
import scrumweb.project.repository.ProjectRepository;
import scrumweb.projectfield.repository.ProjectFieldRepository;
import scrumweb.user.account.domain.UserAccount;
import scrumweb.user.account.repository.UserAccountRepository;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class IssueService {

    private IssueAsm issueAsm;
    private IssueRepository issueRepository;
    private UserAccountRepository userAccountRepository;
    private SecurityContextService securityContextService;
    private ProjectFieldRepository projectFieldRepository;
    private ProjectRepository projectRepository;
    private UserProfileAsm userProfileAsm;
    private FieldContentConverter fieldContentAsm;
    //private SearchResultsDto searchResultsDto;

    public IssueDetailsDto create(IssueDetailsDto issueDetailsDto, Set<FieldContentDto> fieldContentsDto, String projectKey) {
        final Project project = projectRepository.findByKey(projectKey);
        Set<Issue> issues = project.getIssues();
        String issueKey = project.getKey().concat("-").concat(project.getIssues().size() + 1 + "");
        issues.add(createIssue(issueDetailsDto, fieldContentsDto, issueKey, project));
        projectRepository.save(project);
        return issueDetailsDto;
    }

    protected Issue createIssue(IssueDetailsDto issueDetailsDto, Set<FieldContentDto> fieldContentsDto, String issueKey, Project project) {
        final UserAccount reporter = securityContextService.getCurrentUserAccount();
        Set<UserAccount> assignees = new HashSet<>();
        if (!issueDetailsDto.getAssignees().isEmpty()) {
            assignees = userAccountRepository.findUsers(extractUserNames(issueDetailsDto.getAssignees()));
        }
        Set<FieldContent> fieldContents = extractContents(fieldContentsDto);
        IssueType issueType = getIssueType(project.getIssueTypes(), issueDetailsDto.getIssueType());
        Issue issue = issueAsm.createIssueEntityObject(issueDetailsDto, assignees, reporter, fieldContents, issueType);
        issue.setKey(issueKey);
        return issue;
    }

    public IssueDetailsDto getDetails(Long id) {
        Issue issue = issueRepository.findOne(id);
        Set<UserProfileDto> assignees = issue.getAssignees().stream().map(userAccount -> userProfileAsm.makeUserProfileDto(userAccount, userAccount.getUserProfile())).collect(Collectors.toSet());
        UserProfileDto reporter = userProfileAsm.makeUserProfileDto(issue.getReporter(), issue.getReporter().getUserProfile());
        Set<FieldContentDto> fieldsContentsDto = issue.getFieldContents().stream().map(fieldContent -> fieldContentAsm.createDtoObject(fieldContent)).collect(Collectors.toSet());
        IssueDetailsDto issueDetailsDto = issueAsm.createIssueDetailsDto(issue, assignees, reporter);
        issueDetailsDto.setFieldContents(fieldsContentsDto);
        return issueDetailsDto;
    }

    private Set<String> extractUserNames(Set<UserProfileDto> userProfileDtos) {
        return userProfileDtos.stream()
            .map(UserProfileDto::getUsername)
            .collect(Collectors.toSet());
    }

    private Set<FieldContent> extractContents(Set<FieldContentDto> fieldContentsDto) {
        return fieldContentsDto.stream()
            .map(fieldContentDto -> fieldContentAsm.createObjectEntity(
                projectFieldRepository.findOne(fieldContentDto.getProjectFieldId()), fieldContentDto))
            .collect(Collectors.toSet());
    }

    private IssueType getIssueType(Set<IssueType> issueTypes, String issueType) {
        return issueTypes.stream()
            .filter(i -> i.getName().equalsIgnoreCase(issueType))
            .findFirst()
            .orElse(null);
    }

    private boolean checkIfMember(String username, Issue issue) {
         return projectRepository.findAll().stream()
             .filter(p -> p.getIssues().contains(issue))
             .reduce((a, b) -> null)
             .map(project2 -> project2.getMembers().stream()
                 .map(m -> m.getUserAccount().getUsername())
                 .collect(Collectors.toList()).contains(username))
             .orElse(false);
    }

    public void assignToIssue(Long id, String username) {
            Issue issue = issueRepository.findOne(id);
            UserAccount user = userAccountRepository.findByUsername(username);

            if (checkIfMember(username, issue)) {
                issue.getAssignees().add(user);
                issueRepository.save(issue);
            } else {
                throw new CantAssignToIssueException();
            }
    }

    public void unAssignFromIssue(Long id, String username) {
        Issue issue = issueRepository.findOne(id);
        UserAccount user = userAccountRepository.findByUsername(username);

        if (checkIfMember(username, issue)) {
            issue.getAssignees().remove(user);
            issueRepository.save(issue);
        } else {
            throw new CantAssignToIssueException(username);
        }
    }
}
