package scrumweb.project.service

import common.TestData
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import scrumweb.common.SecurityContextService
import scrumweb.common.asm.IssueAsm
import scrumweb.common.asm.ProjectAsm
import scrumweb.common.asm.UserProfileAsm
import scrumweb.dto.issue.IssueDto
import scrumweb.exception.ProjectAlreadyExsistsException
import scrumweb.exception.ProjectNotFoundException
import scrumweb.issue.domain.Issue
import scrumweb.issue.domain.Issue.Priority
import scrumweb.issue.domain.IssueType
import scrumweb.project.domain.Project
import scrumweb.project.repository.ProjectRepository
import scrumweb.security.JwtTokenUtil
import scrumweb.security.JwtUserDetailsServiceImpl
import scrumweb.security.controller.AuthenticationController
import scrumweb.user.account.domain.UserAccount
import scrumweb.user.account.repository.UserAccountRepository
import scrumweb.user.account.service.UserAccountService
import spock.lang.Specification
import spock.lang.Subject

class ProjectServiceTest extends Specification{

    def securityContextService = Mock(SecurityContextService)

    def projectRepository = Mock(ProjectRepository)
    def userAccountRepository = Mock(UserAccountRepository)
    def projectAsm = Mock(ProjectAsm)
    def authentication = Mock(Authentication)
    def securityContext = Mock(SecurityContext)
    def jwtUserDetailsService = Mock(JwtUserDetailsServiceImpl)
    def userDetails = Mock(UserDetails)
    def issueAsm = Mock(IssueAsm)
    def userProfileAsm = Mock(UserProfileAsm)
    private final static String USERNAME = "testUser"
    private final static String PASSWORD = "testUser"

    def setup() {
        securityContext.getAuthentication() >> authentication
        authentication.getName() >> USERNAME
        jwtUserDetailsService.loadUserByUsername(USERNAME) >> userDetails
        userAccountRepository.findByUsername(USERNAME) >> TestData.USER_ACCOUNT
        userDetails.getUsername() >> USERNAME
        securityContextService.getCurrentUserAccount() >> TestData.USER_ACCOUNT

        SecurityContextHolder.setContext(securityContext)
        securityContextService = new SecurityContextService(userAccountRepository)
    }

    @Subject
    def projectService = new ProjectService(projectAsm, projectRepository, userAccountRepository, securityContextService, issueAsm, userProfileAsm)

    def "should save project to database"() {
        given:
        UserAccount projectOwner = TestData.USER_ACCOUNT
        projectOwner.setProjects(new ArrayList<Project>(Arrays.asList(TestData.PROJECT)))

        when:
        projectService.create(TestData.PROJECT_DTO)

        then:
        1 * projectAsm.makeProject(TestData.PROJECT_DTO) >> TestData.PROJECT
        1 * projectRepository.findByKey(TestData.PROJECT_DTO.getProjectKey())
        1 * projectAsm.makeProjectMember(*_)
        1 * userAccountRepository.save(projectOwner)
    }

    def "should throw exception when project key exists"(){
        when:
        projectService.create(TestData.PROJECT_DTO)

        then:
        1 * projectRepository.findByKey(TestData.PROJECT.getKey()) >> TestData.PROJECT
        ProjectAlreadyExsistsException ex = thrown()
        ex.message == "Project with key testkey already exists!"
    }

    def "should edit project name" (){
        when:
        projectService.editName("edited", 1L)

        then:
        1 * projectRepository.findOne(1L) >> TestData.PROJECT
    }

    def "should throw exception project not found" (){
        when:
        projectService.editName("edited", 1L)

        then:
        1 * projectRepository.findOne(1L) >> null
        ProjectNotFoundException ex = thrown()
        ex.message == "Project with id 1 not found!"
    }

    def "should get project details"() {
        given:
        def anyKey = "anykey"
        def anyId = 1L
        def anySummary = "anySummary"
        def anyType = "anyType"
        def anyPriority = "anyPriority"
        def anyAssignee = "anyAssignee"

        def summary = "summary"
        def description = "description"
        def estimateTime = "1d"
        def remainingTime = "1d"
        def issuetype = "TASK"

        Set<UserAccount> users = new HashSet<>(Arrays.asList(TestData.USER_ACCOUNT))
        IssueType issueType = new IssueType(issuetype, TestData.PROJECT)
        Issue issue = new Issue(summary, description, users, TestData.USER_ACCOUNT, estimateTime, remainingTime, Priority.HIGH, issueType, null)

        def issues = new HashSet<>(Arrays.asList(issue))
        def anyNames = new HashSet<>(Arrays.asList(anyAssignee, anyAssignee))
        def anyIssueDto = new IssueDto(anyId, anySummary, anyType, anyPriority, anyNames)
        def project = TestData.PROJECT
        project.setIssues(issues)
        project.setOwner(TestData.USER_ACCOUNT)

        when:
        projectService.getProjectDetails(anyKey)

        then:
        1 * projectRepository.findByKey(anyKey) >> TestData.PROJECT
        1 * projectAsm.makeProjectDto(TestData.PROJECT) >> TestData.PROJECT_DTO
        1 * issueAsm.createIssueDto(_) >> anyIssueDto
        1 * projectAsm.makeProjectDetailsDro(*_)
    }

}
