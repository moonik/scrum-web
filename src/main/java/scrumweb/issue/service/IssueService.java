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
import scrumweb.dto.ListElementsContainerDto;
import scrumweb.dto.RadioButtonContainerDto;
import scrumweb.dto.TextAreaDto;
import scrumweb.dto.UserProfileDto;
import scrumweb.issue.domain.Issue;
import scrumweb.issue.domain.IssueType;
import scrumweb.issue.field.CheckBoxContent;
import scrumweb.issue.field.FieldContent;
import scrumweb.issue.field.InputFieldContent;
import scrumweb.issue.field.ListContent;
import scrumweb.issue.field.RadioButtonContent;
import scrumweb.issue.field.TextAreaContent;
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
        Set<FieldContent> fieldContents = collectFieldContents(issueDetailsDto.getFields());
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

    private FieldsDto collectFields(Set<FieldContent> fieldsContent) {
        Set<InputFieldDto> inputFieldDtos = fieldsContent.stream().filter(f -> f instanceof InputFieldContent).map(f -> fieldAsm.createInputFieldDtoConent(((InputFieldContent) f))).collect(Collectors.toSet());
        Set<TextAreaDto> textAreaDtos = fieldsContent.stream().filter(f -> f instanceof TextAreaContent).map(f -> fieldAsm.createTextAreaDtoContent(((TextAreaContent) f))).collect(Collectors.toSet());
        Set<CheckBoxContainerDto> checkBoxContainerDtos = fieldsContent.stream().filter(f -> f instanceof CheckBoxContent).map(f -> fieldAsm.createCheckBoxContainerDtoContent(((CheckBoxContent) f))).collect(Collectors.toSet());
        Set<RadioButtonContainerDto> radioButtonDtos = fieldsContent.stream().filter(f -> f instanceof RadioButtonContent).map(f -> fieldAsm.createRadioButtonContainerDtoContent(((RadioButtonContent) f))).collect(Collectors.toSet());
        Set<ListElementsContainerDto> listElementsContainerDtos = fieldsContent.stream().filter(f -> f instanceof ListContent).map(f -> fieldAsm.createListElementsContainerDtoContent(((ListContent) f))).collect(Collectors.toSet());
        return new FieldsDto(inputFieldDtos, checkBoxContainerDtos, listElementsContainerDtos, radioButtonDtos, textAreaDtos);
    }

    private Set<FieldContent> collectFieldContents(FieldsDto fieldsDto) {
        Set<FieldContent> fieldContents = fieldsDto.getInputFieldDtos().stream().map(f -> fieldAsm.createFieldContentInputField(f, projectFieldRepository.getOne(f.getId()))).collect(Collectors.toSet());
        fieldContents.addAll(fieldsDto.getCheckBoxContainerDtos().stream().map(f -> fieldAsm.createCheckBoxContent(projectFieldRepository.getOne(f.getId()),f)).collect(Collectors.toSet()));
        fieldContents.addAll(fieldsDto.getRadioButtonContainerDtos().stream().map(f -> fieldAsm.createRadioButtonContent(projectFieldRepository.getOne(f.getId()), f)).collect(Collectors.toSet()));
        fieldContents.addAll(fieldsDto.getTextAreaDtos().stream().map(f -> fieldAsm.createTextAreaContent(projectFieldRepository.getOne(f.getId()), f)).collect(Collectors.toSet()));
        fieldContents.addAll(fieldsDto.getListElementsContainerDtos().stream().map(f -> fieldAsm.createListContent(projectFieldRepository.getOne(f.getId()), f)).collect(Collectors.toSet()));
        return fieldContents;
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

    private Set<ProjectField> getRadioButtons(Set<RadioButtonContainerDto> radioButtonContainerDtos) {
        return radioButtonContainerDtos.stream()
                .map(field -> fieldAsm.createRadioButtonContainer(field))
                .collect(Collectors.toSet());
    }

    private Set<ProjectField> getTextArea(Set<TextAreaDto> textAreaDtos) {
        return textAreaDtos.stream()
                .map(t -> fieldAsm.createTextArea(t))
                .collect(Collectors.toSet());
    }

    private Set<ProjectField> getListContainer(Set<ListElementsContainerDto> listElementsContainerDtos) {
        return listElementsContainerDtos.stream()
                .map(l -> fieldAsm.createListContainer(l))
                .collect(Collectors.toSet());
    }

    private Set<UserAccount> getAssignees(Set<UserProfileDto> userProfileDtos) {
        return userProfileDtos.stream()
                .map(u -> userAccountRepository.findByUsername(u.getUsername()))
                .collect(Collectors.toSet());
    }
}
