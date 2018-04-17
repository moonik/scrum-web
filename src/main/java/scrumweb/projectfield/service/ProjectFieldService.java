package scrumweb.projectfield.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import scrumweb.common.asm.projectfield.ProjectFieldConverter;
import scrumweb.dto.projectfield.ProjectFieldDto;
import scrumweb.exception.IssueTypeDoesNotExists;
import scrumweb.issue.domain.IssueType;
import scrumweb.issue.repository.IssueTypeRepository;
import scrumweb.project.domain.Project;
import scrumweb.project.repository.ProjectRepository;
import scrumweb.projectfield.domain.ProjectField;
import scrumweb.projectfield.repository.ProjectFieldRepository;

import java.util.Comparator;
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

    public Set<ProjectFieldDto> createFields(Set<ProjectFieldDto> projectFieldsDto, String issuetype, String projectKey) {
        Project project = projectRepository.findByKey(projectKey);
        IssueType issueType = findIssueType(project.getIssueTypes(), issuetype);
        if (issueType != null) {
            Set<ProjectField> issueTypeFields = issueType.getFields();
            issueTypeFields.addAll(createEntities(projectFieldsDto));
            issueType = issueTypeRepository.saveAndFlush(issueType);
            return issueType.getFields().stream()
                    .map(f -> projectFieldAsm.createDtoObject(f))
                    .collect(Collectors.toSet());
        } else
            throw new IssueTypeDoesNotExists(issuetype);
    }

    public Set<ProjectFieldDto> getIssueFields(String issueType, String projectKey) {
        final Project project = projectRepository.findByKey(projectKey);
        final IssueType issueTypeFromDb = findIssueType(project.getIssueTypes(), issueType);
        return issueTypeFromDb.getFields().stream()
                .map(field -> projectFieldAsm.createDtoObject(field))
                .sorted(Comparator.comparingLong(ProjectFieldDto::getId))
                .collect(Collectors.toSet());
    }

    public Set<ProjectFieldDto> removeField(Long id, String projectKey, String issueType) {
        IssueType issuetype = findIssueType(projectRepository.findByKey(projectKey).getIssueTypes(), issueType);
        ProjectField projectField = projectFieldRepository.getOne(id);
        Set<ProjectField> projectFields = issuetype.getFields();
        projectFields.remove(projectField);
        issueTypeRepository.saveAndFlush(issuetype);
        return issuetype.getFields().stream()
                .map(f -> projectFieldAsm.createDtoObject(f))
                .collect(Collectors.toSet());
    }

    private IssueType findIssueType(Set<IssueType> issueTypes, String issuetype) {
        return issueTypes.stream()
                .filter(type -> type.getName().equalsIgnoreCase(issuetype))
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
