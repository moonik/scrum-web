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
import scrumweb.project.domain.Project
import scrumweb.project.repository.ProjectRepository
import scrumweb.projectfield.domain.ProjectField
import scrumweb.projectfield.domain.ProjectField.FieldType
import scrumweb.projectfield.repository.ProjectFieldRepository
import scrumweb.user.account.domain.UserAccount
import scrumweb.user.account.repository.UserAccountRepository
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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

    def issueId = 1L
    def key = null
    def summary = "summary"
    def description = "description"
    def estimateTime = "1d"
    def remainingTime = "1d"
    def priority = "HIGH"
    def issueType = "TASK"

    LocalDateTime now = LocalDateTime.now()
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd HH:mm")
    String date = now.format(formatter)

    @Subject
    def issueService = new IssueService(issueAsm, issueRepository, userAccountRepository, securityContextService, projectFieldRepository, projectRepository, userProfileAsm, fieldContentAsm)

    def "should create issue"() {
        given:
        UserProfileDto userProfileDto = new UserProfileDto(profileId, firstname, lastname, photo, username)
        Set<UserProfileDto> assignees = new HashSet<>(Arrays.asList(userProfileDto))
        FieldContentDto fieldContentDto = new InputFieldContentDto(projectFieldId, projectFieldName, content)
        Set<FieldContentDto> fieldContentDtos = new HashSet<>(Arrays.asList(fieldContentDto))
        FieldContent inputFieldContent = new InputFieldContent(projectField, content)
        Set<FieldContent> fieldContents = new HashSet<>(Arrays.asList(inputFieldContent))
        IssueDetailsDto issueDetailsDto = new IssueDetailsDto(issueId, key, summary, description, assignees, userProfileDto, estimateTime, remainingTime, priority, issueType, date, date)
        Set<UserAccount> users = new HashSet<>(Arrays.asList(TestData.USER_ACCOUNT))
        IssueType issueType = new IssueType(issueType, TestData.PROJECT)
        Set<IssueType> issueTypes = new HashSet<>(Arrays.asList(issueType))
        Issue issue = new Issue(summary, description, users, TestData.USER_ACCOUNT, estimateTime, remainingTime, Priority.HIGH, issueType, fieldContents, now)
        Project project = TestData.PROJECT
        project.setIssueTypes(issueTypes)

        when:
        Issue createdIssue = issueService.createIssue(issueDetailsDto, fieldContentDtos, project)

        then:
        1 * securityContextService.getCurrentUserAccount() >> TestData.USER_ACCOUNT
        1 * userAccountRepository.findUsers(_) >> users
        1 * projectFieldRepository.findOne(projectFieldId) >> projectField
        1 * fieldContentAsm.createObjectEntity(projectField, fieldContentDto) >> inputFieldContent
        1 * issueAsm.createIssueEntityObject(issueDetailsDto, users, TestData.USER_ACCOUNT, fieldContents, issueType) >> issue
        createdIssue == issue
    }

    def "should get issue"() {
        given:
        UserProfileDto userProfileDto = new UserProfileDto(profileId, firstname, lastname, photo, username)
        Set<UserProfileDto> assignees = new HashSet<>(Arrays.asList(userProfileDto))
        FieldContent inputFieldContent = new InputFieldContent(projectField, content)
        Set<FieldContent> fieldContents = new HashSet<>(Arrays.asList(inputFieldContent))
        IssueDetailsDto issueDetailsDto = new IssueDetailsDto(issueId, key, summary, description, assignees, userProfileDto, estimateTime, remainingTime, priority, issueType, date, date)
        Set<UserAccount> users = new HashSet<>(Arrays.asList(TestData.USER_ACCOUNT))
        IssueType issueType = new IssueType(issueType, TestData.PROJECT)
        Issue createdIssue = new Issue(summary, description, users, TestData.USER_ACCOUNT, estimateTime, remainingTime, Priority.HIGH, issueType, fieldContents, now)

        and:
        createdIssue.getAssignees() >> users
        createdIssue.getFieldContents() >> fieldContents

        when:
        IssueDetailsDto returnedIssue = issueService.getDetails(issueId)

        then:
        1 * issueRepository.findOne(issueId) >> createdIssue
        2 * userProfileAsm.makeUserProfileDto(*_) >> userProfileDto
        1 * fieldContentAsm.createDtoObject(inputFieldContent)
        1 * issueAsm.createIssueDetailsDto(*_) >> issueDetailsDto
        issueDetailsDto == returnedIssue
    }

}
