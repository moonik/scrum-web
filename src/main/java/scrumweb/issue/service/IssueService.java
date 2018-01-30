package scrumweb.issue.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import scrumweb.common.SecurityContextService;
import scrumweb.common.asm.FieldAsm;
import scrumweb.common.asm.IssueAsm;
import scrumweb.common.asm.UserProfileAsm;
import scrumweb.dto.IssueDetailsDto;
import scrumweb.dto.ProjectFieldDto;
import scrumweb.dto.UserProfileDto;
import scrumweb.issue.domain.Issue;
import scrumweb.issue.domain.IssueType;
import scrumweb.issue.field.FieldContent;
import scrumweb.issue.field.InputFieldContent;
import scrumweb.issue.repository.FieldContentRepository;
import scrumweb.issue.repository.IssueRepository;
import scrumweb.issue.repository.IssueTypeRepository;
import scrumweb.project.domain.Project;
import scrumweb.project.field.ProjectField;
import scrumweb.project.repository.ProjectFieldRepository;
import scrumweb.project.repository.ProjectRepository;
import scrumweb.user.account.domain.UserAccount;
import scrumweb.user.account.repository.UserAccountRepository;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class IssueService {
    private IssueAsm issueAsm;
    private FieldAsm fieldAsm;
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
        final Project project = projectRepository.findByName("project name");
        Set<FieldContent> fieldContents = getInputFieldsContent(issueDetailsDto.getFields());
        IssueType issueType = issueTypeRepository.findByName(issueDetailsDto.getIssueType());
        Set<Issue> projectIssues = project.getIssues();
        Set<UserAccount> assignees = new HashSet<>();

        if (issueDetailsDto.getAssignees() != null) {
            assignees.addAll(getAssignees(issueDetailsDto.getAssignees()));
        }

        fieldContentRepository.save(fieldContents);
        Issue issue = issueAsm.makeIssue(issueDetailsDto, assignees, reporter, fieldContents, issueType);
        projectIssues.add(issue);
        issueRepository.save(projectIssues);
        projectRepository.save(project);
        return issueDetailsDto;
    }

    public IssueDetailsDto getIssue(Long id) {
        final Issue issue = issueRepository.getOne(id);
        Set<UserProfileDto> assignees = issue.getAssignee().stream().map(assignee -> userProfileAsm.makeUserProfileDto(assignee, assignee.getUserProfile())).collect(Collectors.toSet());
        UserProfileDto reporter = userProfileAsm.makeUserProfileDto(issue.getReporter(), issue.getReporter().getUserProfile());
        Set<ProjectFieldDto> projectFieldsDto = getInputFieldContent(issue.getFieldContents());
        return issueAsm.createIssueDetailsDto(issue, assignees, reporter, projectFieldsDto);
    }

    public void createFields(Set<ProjectFieldDto> projectFieldsDto, String issueType) {
        Set<ProjectField> projectFields = getCheckBoxes(projectFieldsDto);
        projectFields.addAll(getRadioButtons(projectFieldsDto));
        projectFields.addAll(getInputFields(projectFieldsDto));
        saveProjectFields(projectFields, issueType);
    }

    private void saveProjectFields(Set<ProjectField> projectFields, String issueTyp) {
        final IssueType issueType = issueTypeRepository.findByName(issueTyp.toUpperCase());
        projectFieldRepository.save(projectFields);
        Set<ProjectField> projectFieldSet = issueType.getFields();
        projectFieldSet.addAll(projectFields);
        issueTypeRepository.save(issueType);
    }

    private Set<ProjectField> getInputFields(Set<ProjectFieldDto> projectFields) {
        return projectFields.stream()
                .filter(f -> f.getFieldType().equalsIgnoreCase("INPUT_FIELD"))
                .map(f -> fieldAsm.createInputField(f))
                .collect(Collectors.toSet());
    }

    private Set<ProjectFieldDto> getInputFieldContent(Set<FieldContent> fieldContents) {
        return fieldContents.stream()
                .map(fieldContent -> fieldAsm.createTextFieldContent(fieldContent.getProjectField(), ((InputFieldContent) fieldContent)))
                .collect(Collectors.toSet());
    }

    private Set<ProjectField> getCheckBoxes(Set<ProjectFieldDto> projectFieldsDto) {
        return projectFieldsDto.stream()
                .filter(field -> field.getFieldType().equalsIgnoreCase("CHECKBOX"))
                .map(field -> fieldAsm.createCheckBoxContainer(field))
                .collect(Collectors.toSet());
    }

    private Set<ProjectField> getRadioButtons(Set<ProjectFieldDto> projectFieldsDto) {
        return projectFieldsDto.stream()
                .filter(field -> field.getFieldType().equalsIgnoreCase("RADIO_BUTTON"))
                .map(field -> fieldAsm.createRadioButtonContainer(field))
                .collect(Collectors.toSet());
    }

    private Set<UserAccount> getAssignees(Set<UserProfileDto> userProfileDtos) {
        return userProfileDtos.stream()
                .map(u -> userAccountRepository.findByUsername(u.getUsername()))
                .collect(Collectors.toSet());
    }

    private Set<FieldContent> getInputFieldsContent(Set<ProjectFieldDto> fields) {
        return fields.stream()
                .map(field -> fieldAsm.createFieldContentInputField(field.getTextField(),
                        projectFieldRepository.getOne(field.getTextField().getId())))
                .collect(Collectors.toSet());
    }
}
