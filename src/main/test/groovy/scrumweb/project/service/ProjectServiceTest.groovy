package scrumweb.project.service

import common.TestData
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import scrumweb.common.SecurityContextService
import scrumweb.common.asm.ProjectAsm
import scrumweb.exception.ProjectAlreadyExsistsException
import scrumweb.exception.ProjectNotFoundException
import scrumweb.project.repository.ProjectRepository
import scrumweb.project.ProjectService
import scrumweb.security.JwtTokenUtil
import scrumweb.security.JwtUserDetailsServiceImpl
import scrumweb.security.controller.AuthenticationController
import scrumweb.user.account.repository.UserAccountRepository
import scrumweb.user.account.service.UserAccountService
import spock.lang.Specification
import spock.lang.Subject

class ProjectServiceTest extends Specification{

    def securityContextService = Mock(SecurityContextService)
    def mockMvc

    def projectRepository = Mock(ProjectRepository)
    def userAccountRepository = Mock(UserAccountRepository)
    def projectAsm = Mock(ProjectAsm)
    def authentication = Mock(Authentication)
    def securityContext = Mock(SecurityContext)
    def jwtUserDetailsService = Mock(JwtUserDetailsServiceImpl)
    def userDetails = Mock(UserDetails)
    def userAccountService = Mock(UserAccountService)
    def jwtTokenUtil = Mock(JwtTokenUtil)
    def authenticationManager = Mock(AuthenticationManager)

    private final static String USERNAME = "testUser"
    private final static String PASSWORD = "testUser"
    private final static String JSON_CONTENT = "{username: " + USERNAME + ", password: " + PASSWORD + "}"

    def setup() {
        securityContext.getAuthentication() >> authentication
        authentication.getName() >> USERNAME
        jwtUserDetailsService.loadUserByUsername(USERNAME) >> userDetails
        userAccountRepository.findByUsername(USERNAME) >> TestData.USER_ACCOUNT
        userDetails.getUsername() >> USERNAME
        securityContextService.getCurrentUserAccount() >> TestData.USER_ACCOUNT

        SecurityContextHolder.setContext(securityContext)
        securityContextService = new SecurityContextService(userAccountRepository)

        AuthenticationController authenticationController = new AuthenticationController(userAccountService, userAccountRepository, authenticationManager, jwtTokenUtil, jwtUserDetailsService)
        mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build()
    }

    @Subject
    def projectService = new ProjectService(projectAsm, projectRepository, userAccountRepository, securityContextService)

    def "should save project to database"() {
        when:
        projectService.create(TestData.PROJECT_DTO)

        then:
        1 * projectAsm.makeProject(TestData.PROJECT_DTO) >> TestData.PROJECT
        1 * projectRepository.findByKey(TestData.PROJECT_DTO.getProjectKey())
        1 * projectRepository.save(TestData.PROJECT)
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

}
