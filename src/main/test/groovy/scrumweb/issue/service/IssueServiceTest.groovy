package scrumweb.issue.service

import common.TestData
import scrumweb.common.SecurityContextService
import scrumweb.common.asm.IssueAsm
import scrumweb.common.asm.UserProfileAsm
import scrumweb.common.asm.fieldcontent.FieldContentConverter
import scrumweb.dto.fieldcontent.FieldContentDto
import scrumweb.dto.fieldcontent.InputFieldContentDto
import scrumweb.dto.issue.IssueDetailsDto
import scrumweb.dto.user.UserProfileDto
import scrumweb.issue.domain.Issue
import scrumweb.issue.domain.Issue.Priority
import scrumweb.issue.domain.IssueType
import scrumweb.issue.fieldcontent.FieldContent
import scrumweb.issue.fieldcontent.InputFieldContent
import scrumweb.issue.repository.IssueRepository
import scrumweb.issue.repository.IssueTypeRepository
import scrumweb.project.repository.ProjectRepository
import scrumweb.projectfield.domain.ProjectField
import scrumweb.projectfield.domain.ProjectField.FieldType
import scrumweb.projectfield.repository.ProjectFieldRepository
import scrumweb.user.account.domain.UserAccount
import scrumweb.user.account.repository.UserAccountRepository
import spock.lang.Specification
import spock.lang.Subject

class IssueServiceTest extends Specification {

    def issueAsm = Mock(IssueAsm)
    def issueRepository = Mock(IssueRepository)
    def userAccountRepository = Mock(UserAccountRepository)
    def securityContextService = Mock(SecurityContextService)
    def projectFieldRepository = Mock(ProjectFieldRepository)
    def issueTypeRepository = Mock(IssueTypeRepository)
    def projectRepository = Mock(ProjectRepository)
    def userProfileAsm = Mock(UserProfileAsm)
    def fieldContentAsm = Mock(FieldContentConverter)

    def profileId = 1L
    def firstname = "firstname"
    def lastname = "lastname"
    def photo = "photo"
    def username = "username"

    def projectFieldId = 1L
    def projectFieldName = "projectFieldName"
    def content = "content"

    def issueId = null
    def key = null
    def summary = "summary"
    def description = "description"
    def estimateTime = "1d"
    def remainingTime = "1d"
    def priority = "HIGH"
    def issueType = "TASK"

    @Subject
    def issueService = new IssueService(issueAsm, issueRepository, userAccountRepository, securityContextService, projectFieldRepository, issueTypeRepository, projectRepository, userProfileAsm, fieldContentAsm)

    def "should save issue"() {
        given:
        UserProfileDto userProfileDto = new UserProfileDto(profileId, firstname, lastname, photo, username)
        Set<UserProfileDto> assigness = new HashSet<>(Arrays.asList(userProfileDto))
        FieldContentDto fieldContentDto = new InputFieldContentDto(projectFieldId, projectFieldName, content)
        Set<FieldContentDto> fieldContentDtos = new HashSet<>(Arrays.asList(fieldContentDto))
        ProjectField projectField = new ProjectField(FieldType.INPUT_FIELD, projectFieldName, true)
        FieldContent inputFieldContent = new InputFieldContent(projectField, content)
        Set<FieldContent> fieldContents = new HashSet<>(Arrays.asList(inputFieldContent))
        IssueDetailsDto issueDetailsDto = new IssueDetailsDto(issueId, key, summary, description, assigness, userProfileDto, estimateTime, remainingTime, priority, issueType)
        Set<UserAccount> users = new HashSet<>(Arrays.asList(TestData.USER_ACCOUNT))
        IssueType issueType = new IssueType(issueType, TestData.PROJECT)
        Issue createdIssue = new Issue(summary, description, users, TestData.USER_ACCOUNT, estimateTime, remainingTime, Priority.HIGH, issueType, fieldContents)

        when:
        Issue issue = issueService.createIssue(issueDetailsDto, fieldContentDtos)

        then:
        1 * securityContextService.getCurrentUserAccount() >> TestData.USER_ACCOUNT
        1 * userAccountRepository.findUsers(_) >> users
        1 * projectFieldRepository.findOne(projectFieldId) >> projectField
        1 * fieldContentAsm.createObjectEntity(projectField, fieldContentDto) >> inputFieldContent
        1 * issueTypeRepository.findByName(_) >> issueType
        1 * issueAsm.createIssueEntityObject(issueDetailsDto, users, TestData.USER_ACCOUNT, fieldContents, issueType)
        1 * issueRepository.save(_) >> createdIssue
        createdIssue == issue
    }

}
