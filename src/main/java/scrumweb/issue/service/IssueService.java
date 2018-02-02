package scrumweb.issue.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import scrumweb.common.SecurityContextService;
import scrumweb.common.asm.projectfield.ProjectFieldAsm;
import scrumweb.common.asm.IssueAsm;
import scrumweb.common.asm.UserProfileAsm;
import scrumweb.dto.projectfield.CheckBoxContainerDto;
import scrumweb.dto.projectfield.InputFieldDto;
import scrumweb.dto.IssueDetailsDto;
import scrumweb.dto.projectfield.ListElementsContainerDto;
import scrumweb.dto.projectfield.RadioButtonContainerDto;
import scrumweb.dto.projectfield.TextAreaDto;
import scrumweb.dto.UserProfileDto;
import scrumweb.issue.domain.Issue;
import scrumweb.issue.domain.IssueType;
import scrumweb.issue.field.InputFieldContent;
import scrumweb.issue.field.ListContent;
import scrumweb.issue.field.RadioButtonContent;
import scrumweb.issue.field.TextAreaContent;
import scrumweb.issue.repository.FieldContentRepository;
import scrumweb.issue.field.CheckBoxContent;
import scrumweb.issue.field.FieldContent;
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
        Set<FieldContent> fieldContents = collectFieldContents(issueDetailsDto.getFields());
        IssueType issueType = issueTypeRepository.findByName(issueDetailsDto.getIssueType());
        Set<Issue> projectIssues = project.getIssues();
        Set<UserAccount> assignees = getAssignees(issueDetailsDto.getAssignees());

        fieldContentRepository.save(fieldContents);
        Issue issue = issueAsm.makeIssue(issueDetailsDto, assignees, reporter, fieldContents, issueType);
        projectIssues.add(issue);
        issueRepository.save(projectIssues);
        projectRepository.save(project);
        return issueDetailsDto;
    }

    public IssueDetailsDto getIssue(Long id) {
        final Issue issue = issueRepository.getOne(id);
        Set<UserProfileDto> assignees = issue.getAssignees().stream()
                .map(assignee -> userProfileAsm.makeUserProfileDto(assignee, assignee.getUserProfile()))
                .collect(Collectors.toSet());

        UserProfileDto reporter = userProfileAsm.makeUserProfileDto(issue.getReporter(), issue.getReporter().getUserProfile());
        FieldsDto fieldsDto = collectFields(issue.getFieldContents());
        return issueAsm.createIssueDetailsDto(issue, assignees, reporter, fieldsDto);
    }

    public void createFields(FieldsDto fieldsDto, String issueType) {
        Set<ProjectField> projectFields = getCheckBoxes(fieldsDto.getCheckBoxContainerDtos());
        projectFields.addAll(getRadioButtons(fieldsDto.getRadioButtonContainerDtos()));
        projectFields.addAll(getInputFields(fieldsDto.getInputFieldDtos()));
        projectFields.addAll(getListContainer(fieldsDto.getListElementsContainerDtos()));
        projectFields.addAll(getTextArea(fieldsDto.getTextAreaDtos()));
        saveProjectFields(projectFields, issueType);
    }

    protected FieldsDto collectFields(Set<FieldContent> fieldsContent) {
        Set<InputFieldDto> inputFieldDtos = fieldsContent.stream()
                .filter(f -> f instanceof InputFieldContent)
                .map(f -> projectFieldAsm.createInputFieldDtoConent(((InputFieldContent) f)))
                .collect(Collectors.toSet());
        Set<TextAreaDto> textAreaDtos = fieldsContent.stream()
                .filter(f -> f instanceof TextAreaContent)
                .map(f -> projectFieldAsm.createTextAreaDtoContent(((TextAreaContent) f)))
                .collect(Collectors.toSet());
        Set<CheckBoxContainerDto> checkBoxContainerDtos = fieldsContent.stream()
                .filter(f -> f instanceof CheckBoxContent)
                .map(f -> projectFieldAsm.createCheckBoxContainerDtoContent(((CheckBoxContent) f)))
                .collect(Collectors.toSet());
        Set<RadioButtonContainerDto> radioButtonDtos = fieldsContent.stream()
                .filter(f -> f instanceof RadioButtonContent)
                .map(f -> projectFieldAsm.createRadioButtonContainerDtoContent(((RadioButtonContent) f)))
                .collect(Collectors.toSet());
        Set<ListElementsContainerDto> listElementsContainerDtos = fieldsContent.stream()
                .filter(f -> f instanceof ListContent)
                .map(f -> projectFieldAsm.createListElementsContainerDtoContent(((ListContent) f)))
                .collect(Collectors.toSet());
        return new FieldsDto(inputFieldDtos, checkBoxContainerDtos, listElementsContainerDtos, radioButtonDtos, textAreaDtos);
    }

    protected Set<FieldContent> collectFieldContents(FieldsDto fieldsDto) {
        Set<FieldContent> fieldContents = fieldsDto.getInputFieldDtos().stream()
                .map(f -> projectFieldAsm.createFieldContentInputField(f, projectFieldRepository.getOne(f.getId())))
                .collect(Collectors.toSet());
        fieldContents.addAll(fieldsDto.getCheckBoxContainerDtos().stream()
                .map(f -> projectFieldAsm.createCheckBoxContent(projectFieldRepository.getOne(f.getId()),f))
                .collect(Collectors.toSet()));
        fieldContents.addAll(fieldsDto.getRadioButtonContainerDtos().stream()
                .map(f -> projectFieldAsm.createRadioButtonContent(projectFieldRepository.getOne(f.getId()), f))
                .collect(Collectors.toSet()));
        fieldContents.addAll(fieldsDto.getTextAreaDtos().stream()
                .map(f -> projectFieldAsm.createTextAreaContent(projectFieldRepository.getOne(f.getId()), f))
                .collect(Collectors.toSet()));
        fieldContents.addAll(fieldsDto.getListElementsContainerDtos().stream()
                .map(f -> projectFieldAsm.createListContent(projectFieldRepository.getOne(f.getId()), f))
                .collect(Collectors.toSet()));
        return fieldContents;
    }

    protected void saveProjectFields(Set<ProjectField> projectFields, String issueTyp) {
        final IssueType issueType = issueTypeRepository.findByName(issueTyp.toUpperCase());
        projectFieldRepository.save(projectFields);
        Set<ProjectField> projectFieldSet = issueType.getFields();
        projectFieldSet.addAll(projectFields);
        issueTypeRepository.save(issueType);
    }

    protected Set<ProjectField> getInputFields(Set<InputFieldDto> inputFieldDtos) {
        return inputFieldDtos.stream()
                .map(f -> projectFieldAsm.createInputField(f))
                .collect(Collectors.toSet());
    }

    protected Set<ProjectField> getCheckBoxes(Set<CheckBoxContainerDto> checkBoxContainerDtos) {
        return checkBoxContainerDtos.stream()
                .map(field -> projectFieldAsm.createCheckBoxContainer(field))
                .collect(Collectors.toSet());
    }

    protected Set<ProjectField> getRadioButtons(Set<RadioButtonContainerDto> radioButtonContainerDtos) {
        return radioButtonContainerDtos.stream()
                .map(field -> projectFieldAsm.createRadioButtonContainer(field))
                .collect(Collectors.toSet());
    }

    protected Set<ProjectField> getTextArea(Set<TextAreaDto> textAreaDtos) {
        return textAreaDtos.stream()
                .map(t -> projectFieldAsm.createTextArea(t))
                .collect(Collectors.toSet());
    }

    protected Set<ProjectField> getListContainer(Set<ListElementsContainerDto> listElementsContainerDtos) {
        return listElementsContainerDtos.stream()
                .map(l -> projectFieldAsm.createListContainer(l))
                .collect(Collectors.toSet());
    }

    protected Set<UserAccount> getAssignees(Set<UserProfileDto> userProfileDtos) {
        return userProfileDtos.stream()
                .map(u -> userAccountRepository.findByUsername(u.getUsername()))
                .collect(Collectors.toSet());
    }
}
