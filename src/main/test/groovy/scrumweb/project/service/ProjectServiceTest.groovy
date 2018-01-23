package scrumweb.project.service

import common.TestData
import org.mockito.Mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.transaction.annotation.Transactional
import scrumweb.App
import scrumweb.common.SecurityContextService
import scrumweb.common.asm.ProjectAsm
import scrumweb.user.account.repository.UserAccountRepository
import scrumweb.user.account.service.UserAccountService
import scrumweb.user.project.domain.Project
import scrumweb.user.project.domain.ProjectMember
import scrumweb.user.project.repository.ProjectRepository
import scrumweb.user.project.service.ProjectService
import spock.lang.Specification

import static org.mockito.Mockito.when

@SpringBootTest(classes=[App.class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class ProjectServiceTest extends Specification{

    @Mock
    SecurityContextService securityContextService

    ProjectService projectService

    @Autowired
    ProjectRepository projectRepository

    @Mock
    ProjectAsm projectAsm

    @Mock
    UserAccountRepository userAccountRepository

    def setup() {
        Project project = new Project("testUser", "testUser", "testUser", null)
        when(projectAsm.makeProject(TestData.PROJECT_DTO)).thenReturn(project)
        when(securityContextService.getCurrentUserAccount()).thenReturn(TestData.USER_ACCOUNT)
        projectService = new ProjectService(projectAsm, projectRepository, userAccountRepository, securityContextService)
    }

    def "should save project to database"() {
        when:
        projectService.create(TestData.PROJECT_DTO)

        then:
        projectRepository.findByName("testUser") != null
    }
}
