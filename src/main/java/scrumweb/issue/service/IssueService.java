package scrumweb.issue.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import scrumweb.common.SecurityContextService;
import scrumweb.common.asm.FieldAsm;
import scrumweb.common.asm.IssueAsm;
import scrumweb.common.asm.UserProfileAsm;
import scrumweb.dto.CheckBoxContainerDto;
import scrumweb.dto.FieldsDto;
import scrumweb.dto.InputFieldDto;
import scrumweb.dto.IssueDetailsDto;
import scrumweb.dto.ProjectFieldDto;
import scrumweb.dto.RadioButtonContainerDto;
import scrumweb.dto.UserProfileDto;
import scrumweb.issue.domain.Issue;
import scrumweb.issue.domain.IssueType;
import scrumweb.issue.field.FieldContent;
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
        final Project project = projectRepository.getOne(projectId);
        FieldsDto fieldsDtos = issueDetailsDto.getFields();
        Set<FieldContent> fieldContents = getInputFieldsContent(fieldsDtos.getInputFieldDtos());
        fieldContents.addAll(getCheckBoxContent(fieldsDtos.getCheckBoxContainerDtos()));
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
        Set<UserProfileDto> assignees = issue.getAssignees().stream().map(assignee -> userProfileAsm.makeUserProfileDto(assignee, assignee.getUserProfile())).collect(Collectors.toSet());
        UserProfileDto reporter = userProfileAsm.makeUserProfileDto(issue.getReporter(), issue.getReporter().getUserProfile());
        FieldsDto projectFieldsDto = issue.getFieldContents().stream().map(field -> fieldAsm.createProjectFieldDto(field)).collect(Collectors.toSet());
        return issueAsm.createIssueDetailsDto(issue, assignees, reporter, projectFieldsDto);
    }

    public void createFields(FieldsDto fieldsDto, String issueType) {
        Set<ProjectField> projectFields = getCheckBoxes(fieldsDto.getCheckBoxContainerDtos());
        projectFields.addAll(getRadioButtons(fieldsDto.getRadioButtonContainerDtos()));
        projectFields.addAll(getInputFields(fieldsDto.getInputFieldDtos()));
        saveProjectFields(projectFields, issueType);
    }

    private void saveProjectFields(Set<ProjectField> projectFields, String issueTyp) {
        final IssueType issueType = issueTypeRepository.findByName(issueTyp.toUpperCase());
        projectFieldRepository.save(projectFields);
        Set<ProjectField> projectFieldSet = issueType.getFields();
        projectFieldSet.addAll(projectFields);
        issueTypeRepository.save(issueType);
    }

    private Set<ProjectField> getInputFields(Set<InputFieldDto> inputFieldDtos) {
        return inputFieldDtos.stream()
                .map(f -> fieldAsm.createInputField(f))
                .collect(Collectors.toSet());
    }

    private Set<ProjectField> getCheckBoxes(Set<CheckBoxContainerDto> checkBoxContainerDtos) {
        return checkBoxContainerDtos.stream()
                .map(field -> fieldAsm.createCheckBoxContainer(field))
                .collect(Collectors.toSet());
    }

    private Set<FieldContent> getCheckBoxContent(Set<CheckBoxContainerDto> checkBoxContainerDtos) {
        return checkBoxContainerDtos.stream()
                .map(field -> fieldAsm.createCheckBoxContent(projectFieldRepository.getOne(field.getId()), field))
                .collect(Collectors.toSet());
    }

    private Set<ProjectField> getRadioButtons(Set<RadioButtonContainerDto> radioButtonContainerDtos) {
        return radioButtonContainerDtos.stream()
                .map(field -> fieldAsm.createRadioButtonContainer(field))
                .collect(Collectors.toSet());
    }

    private Set<UserAccount> getAssignees(Set<UserProfileDto> userProfileDtos) {
        return userProfileDtos.stream()
                .map(u -> userAccountRepository.findByUsername(u.getUsername()))
                .collect(Collectors.toSet());
    }

    private Set<FieldContent> getInputFieldsContent(Set<InputFieldDto> inputFieldDtos) {
        return inputFieldDtos.stream()
                .map(field -> fieldAsm.createFieldContentInputField(field, projectFieldRepository.getOne(field.getId())))
                .collect(Collectors.toSet());
    }
}
