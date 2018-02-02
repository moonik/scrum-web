package scrumweb.issue.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import scrumweb.common.SecurityContextService;
import scrumweb.common.asm.projectfield.ProjectFieldAsm;
import scrumweb.common.asm.IssueAsm;
import scrumweb.common.asm.UserProfileAsm;
import scrumweb.dto.issue.IssueDetailsDto;
import scrumweb.issue.repository.FieldContentRepository;
import scrumweb.issue.repository.IssueRepository;
import scrumweb.issue.repository.IssueTypeRepository;
import scrumweb.project.domain.Project;
import scrumweb.projectfield.repository.ProjectFieldRepository;
import scrumweb.project.repository.ProjectRepository;
import scrumweb.user.account.domain.UserAccount;
import scrumweb.user.account.repository.UserAccountRepository;

@Service
@AllArgsConstructor
public class IssueService {

    private IssueAsm issueAsm;
    private ProjectFieldAsm projectFieldAsm;
    private IssueRepository issueRepository;
    private UserAccountRepository userAccountRepository;
    private SecurityContextService securityContextService;
    private ProjectFieldRepository projectFieldRepository;
    private IssueTypeRepository issueTypeRepository;
    private UserProfileAsm userProfileAsm;
    private ProjectRepository projectRepository;
    private FieldContentRepository fieldContentRepository;

    public IssueDetailsDto create(IssueDetailsDto issueDetailsDto, Long projectId) {
        final UserAccount reporter = securityContextService.getCurrentUserAccount();
        final Project project = projectRepository.getOne(projectId);
    }

//    public IssueDetailsDto create(IssueDetailsDto issueDetailsDto, Long projectId) {
//        final UserAccount reporter = securityContextService.getCurrentUserAccount();
//        final Project project = projectRepository.getOne(projectId);
//        Set<FieldContent> fieldContents = collectFieldContents(issueDetailsDto.getFields());
//        IssueType issueType = issueTypeRepository.findByName(issueDetailsDto.getIssueType());
//        Set<Issue> projectIssues = project.getIssues();
//        Set<UserAccount> assignees = getAssignees(issueDetailsDto.getAssignees());
//
//        fieldContentRepository.save(fieldContents);
//        Issue issue = issueAsm.makeIssue(issueDetailsDto, assignees, reporter, fieldContents, issueType);
//        projectIssues.add(issue);
//        issueRepository.save(projectIssues);
//        projectRepository.save(project);
//        return issueDetailsDto;
//    }

//    public IssueDetailsDto getIssue(Long id) {
//        final Issue issue = issueRepository.getOne(id);
//        Set<UserProfileDto> assignees = issue.getAssignees().stream()
//                .map(assignee -> userProfileAsm.makeUserProfileDto(assignee, assignee.getUserProfile()))
//                .collect(Collectors.toSet());
//
//        UserProfileDto reporter = userProfileAsm.makeUserProfileDto(issue.getReporter(), issue.getReporter().getUserProfile());
//        FieldsDto fieldsDto = collectFields(issue.getFieldContents());
//        return issueAsm.createIssueDetailsDto(issue, assignees, reporter, fieldsDto);
//    }
}
