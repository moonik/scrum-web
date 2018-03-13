package scrumweb.projectfield.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import scrumweb.common.asm.projectfield.ProjectFieldAsm;
import scrumweb.common.asm.projectfield.ProjectFieldConverter;
import scrumweb.dto.projectfield.ProjectFieldDto;
import scrumweb.exception.IssueTypeDoesNotExists;
import scrumweb.issue.domain.IssueType;
import scrumweb.issue.repository.IssueTypeRepository;
import scrumweb.project.domain.Project;
import scrumweb.project.repository.ProjectRepository;
import scrumweb.projectfield.domain.ProjectField;
import scrumweb.projectfield.repository.ProjectFieldRepository;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProjectFieldService {

    private ProjectFieldConverter projectFieldAsm;
    private ProjectRepository projectRepository;
    private IssueTypeRepository issueTypeRepository;
    private ProjectFieldRepository projectFieldRepository;

    public void createFields(Set<ProjectFieldDto> projectFieldsDto, String issuetype, String projectKey) {
        Project project = projectRepository.findByKey(projectKey);
        IssueType issueType = findIssueType(project.getIssueTypes(), issuetype.toUpperCase());
        if (issueType != null) {
            Set<ProjectField> issueTypeFields = issueType.getFields();
            Set<ProjectField> fieldsToBeSaved = createEntities(projectFieldsDto);
            issueTypeFields.addAll(fieldsToBeSaved);
            issueTypeRepository.saveAndFlush(issueType);
        } else
            throw new IssueTypeDoesNotExists(issuetype);
    }

    public Set<ProjectFieldDto> getIssueFields(String issueType, String projectKey) {
        final Project project = projectRepository.findByKey(projectKey);
        final IssueType issueTypeFromDb = findIssueType(project.getIssueTypes(), issueType.toUpperCase());
        return issueTypeFromDb.getFields().stream()
                .map(field -> projectFieldAsm.createDtoObject(field))
                .collect(Collectors.toSet());
    }

    private IssueType findIssueType(Set<IssueType> issueTypes, String issuetype) {
        return issueTypes.stream()
                .filter(type -> type.getName().equals(issuetype))
                .findFirst()
                .orElse(null);
    }

    private Set<ProjectField> createEntities(Set<ProjectFieldDto> projectFieldsDto) {
        Set<ProjectField> fieldsToBeSaved = new HashSet<>();
        projectFieldsDto.forEach(f -> {
            if (f.getId() != null) {
                ProjectField projectField = projectFieldRepository.findOne(f.getId());
                projectField.edit(projectFieldAsm.createEntityObject(f));
                fieldsToBeSaved.add(projectField);
            } else
                fieldsToBeSaved.add(projectFieldAsm.createEntityObject(f));
        });
        return fieldsToBeSaved;
    }
}
