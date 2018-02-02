package scrumweb.projectfield.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import scrumweb.common.asm.projectfield.ProjectFieldAsm;
import scrumweb.common.asm.projectfield.ProjectFieldConverter;
import scrumweb.dto.projectfield.ProjectFieldDto;
import scrumweb.exception.IssueTypeDoesNotExists;
import scrumweb.issue.domain.IssueType;
import scrumweb.issue.repository.IssueTypeRepository;
import scrumweb.projectfield.domain.ProjectField;
import scrumweb.projectfield.repository.ProjectFieldRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProjectFieldService {

    private ProjectFieldConverter projectFieldAsm;
    private ProjectFieldRepository projectFieldRepository;
    private IssueTypeRepository issueTypeRepository;

    public void createFields(Set<ProjectFieldDto> projectFieldsDto, String issueType) {
        final IssueType issueTypeFromDb = issueTypeRepository.findByName(issueType.toUpperCase());
        if (issueTypeFromDb != null) {
            Set<ProjectField> issueTypeFields = issueTypeFromDb.getFields();
            Set<ProjectField> fieldsToBeSaved = projectFieldsDto.stream().map(field -> projectFieldAsm.createEntityObject(field)).collect(Collectors.toSet());
            projectFieldRepository.save(fieldsToBeSaved);
            issueTypeFields.addAll(fieldsToBeSaved);
            issueTypeRepository.save(issueTypeFromDb);
        } else
            throw new IssueTypeDoesNotExists(issueType);
    }

    public Set<ProjectFieldDto> getIssueFields(String issueType) {
        final IssueType issueTypeFromDb = issueTypeRepository.findByName(issueType.toUpperCase());
        return issueTypeFromDb.getFields().stream()
                .map(field -> projectFieldAsm.createDtoObject(field))
                .collect(Collectors.toSet());
    }
}
