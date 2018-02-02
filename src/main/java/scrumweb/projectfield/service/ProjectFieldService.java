package scrumweb.projectfield.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scrumweb.common.asm.projectfield.ProjectFieldAsm;
import scrumweb.dto.projectfield.ProjectFieldDto;
import scrumweb.exception.IssueTypeDoesNotExists;
import scrumweb.issue.domain.IssueType;
import scrumweb.issue.repository.IssueTypeRepository;
import scrumweb.projectfield.domain.ProjectField;
import scrumweb.projectfield.repository.ProjectFieldRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public abstract class ProjectFieldService implements ProjectFieldAsm<ProjectField, ProjectFieldDto> {

    @Autowired
    private ProjectFieldRepository projectFieldRepository;
    @Autowired
    private IssueTypeRepository issueTypeRepository;

    public void createFields(Set<ProjectFieldDto> projectFieldsDto, String issueType) {
        final IssueType issueTypeFromDb = issueTypeRepository.findByName(issueType.toUpperCase());
        if (issueTypeFromDb != null) {
            Set<ProjectField> issueTypeFields = issueTypeFromDb.getFields();
            Set<ProjectField> fieldsToBeSaved = projectFieldsDto.stream().map(this::convertToEntityObject).collect(Collectors.toSet());
            projectFieldRepository.save(fieldsToBeSaved);
            issueTypeFields.addAll(fieldsToBeSaved);
            issueTypeRepository.save(issueTypeFromDb);
        } else
            throw new IssueTypeDoesNotExists(issueType);
    }

    public Set<ProjectFieldDto> getIssueFields(String issueType) {
        final IssueType issueTypeFromDb = issueTypeRepository.findByName(issueType.toUpperCase());
        return issueTypeFromDb.getFields().stream()
                .map(this::convertToDtoObject)
                .collect(Collectors.toSet());
    }
}
