package scrumweb.issue.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import scrumweb.common.SecurityContextService;
import scrumweb.common.asm.FieldAsm;
import scrumweb.common.asm.IssueAsm;
import scrumweb.dto.ProjectFieldDto;
import scrumweb.dto.UserProfileDto;
import scrumweb.issue.domain.IssueType;
import scrumweb.issue.repository.IssueRepository;
import scrumweb.issue.repository.IssueTypeRepository;
import scrumweb.project.field.ProjectField;
import scrumweb.project.repository.ProjectFieldRepository;
import scrumweb.user.account.domain.UserAccount;
import scrumweb.user.account.repository.UserAccountRepository;

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

//    public IssueDetailsDto create(IssueDetailsDto issueDto) {
//        final UserAccount reporter = securityContextService.getCurrentUserAccount();
//
//        Issue issue = issueAsm.makeIssue(issueDto, getAssignees(issueDto.getAssignee()), reporter, )
//    }

    public void createFields(Set<ProjectFieldDto> projectFieldsDto, String issueType) {
        Set<ProjectField> projectFields = getCheckBoxes(projectFieldsDto);
        projectFields.addAll(getRadioButtons(projectFieldsDto));
        projectFields.addAll(getInputFields(projectFieldsDto));
        saveProjectFields(projectFields, issueType);
    }

    private void saveProjectFields(Set<ProjectField> projectFields, String issueTyp) {
        IssueType issueType = issueTypeRepository.findByName(issueTyp.toUpperCase());
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

    private Set<ProjectField> getCheckBoxes(Set<ProjectFieldDto> projectFieldsDto) {
        return projectFieldsDto.stream()
                .filter(field -> field.getFieldType().equalsIgnoreCase("checkbox"))
                .map(field -> fieldAsm.createCheckBoxContainer(field))
                .collect(Collectors.toSet());
    }

    private Set<ProjectField> getRadioButtons(Set<ProjectFieldDto> projectFieldsDto) {
        return projectFieldsDto.stream()
                .filter(field -> field.getFieldType().equalsIgnoreCase("radio-button"))
                .map(field -> fieldAsm.createRadioButtonContainer(field))
                .collect(Collectors.toSet());
    }

    private Set<UserAccount> getAssignees(Set<UserProfileDto> userProfileDtos) {
        return userProfileDtos.stream()
                .map(u -> userAccountRepository.findByUsername(u.getUsername()))
                .collect(Collectors.toSet());
    }
}
