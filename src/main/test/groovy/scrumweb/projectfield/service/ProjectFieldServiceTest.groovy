package scrumweb.projectfield.service

import common.TestData
import scrumweb.common.asm.projectfield.ProjectFieldConverter
import scrumweb.dto.projectfield.InputFieldDto
import scrumweb.dto.projectfield.ProjectFieldDto
import scrumweb.issue.domain.IssueType
import scrumweb.issue.repository.IssueTypeRepository
import scrumweb.project.domain.Project
import scrumweb.project.repository.ProjectRepository
import scrumweb.projectfield.domain.InputField
import scrumweb.projectfield.domain.ProjectField
import spock.lang.Specification
import spock.lang.Subject

class ProjectFieldServiceTest extends Specification {

    def projectFieldAsm = Mock(ProjectFieldConverter)
    def projectRepository = Mock(ProjectRepository)
    def issueTypeRepository = Mock(IssueTypeRepository)

    @Subject
    def projectFieldService = new ProjectFieldService(projectFieldAsm, projectRepository, issueTypeRepository)

    def final FIELD_NAME = "test field"
    def final FIELD_TYPE = "INPUT_FIELD"
    def final IS_REQUIRED = true
    def final MAX_CHARACTERS = 10
    def final MIN_CHARACTERS = 0
    def final FIELD_ID = 1L
    def anyProjectKey = "any project key"
    def final ISSUE_TYPE = "TASK"

    def "should create new project field"() {
        given:
        ProjectFieldDto projectFieldDto = new InputFieldDto(FIELD_ID, FIELD_TYPE, FIELD_NAME, IS_REQUIRED, MAX_CHARACTERS, MIN_CHARACTERS)
        Project project = TestData.PROJECT
        Set<ProjectFieldDto> projectFieldDtos = new HashSet<>(Arrays.asList(projectFieldDto))
        IssueType issueType = new IssueType(ISSUE_TYPE, project)
        Set<IssueType> issueTypes = new HashSet<IssueType>(Arrays.asList(issueType))
        ProjectField projectField = new InputField(ProjectField.FieldType.INPUT_FIELD, FIELD_NAME, IS_REQUIRED, MIN_CHARACTERS, MAX_CHARACTERS)
        Set<ProjectField> projectFields = new HashSet<>(Arrays.asList(projectField))
        issueType.setFields(projectFields)
        project.setIssueTypes(issueTypes)

        when:
        projectFieldService.createFields(projectFieldDtos, ISSUE_TYPE, anyProjectKey)

        then:
        1 * projectRepository.findByKey(anyProjectKey) >> project
        1 * projectFieldAsm.createEntityObject(projectFieldDto) >> projectField
        1 * issueTypeRepository.save(issueType) >> issueType
    }
}
