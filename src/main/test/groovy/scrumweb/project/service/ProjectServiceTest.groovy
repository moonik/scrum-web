package scrumweb.project.service

import common.TestData
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import scrumweb.App
import scrumweb.dto.ProjectDto
import scrumweb.exception.ProjectNotFoundException
import scrumweb.user.project.domain.Project
import scrumweb.user.project.repository.ProjectRepository
import scrumweb.user.project.service.ProjectService
import spock.lang.Specification

@SpringBootTest(classes=[App.class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class ProjectServiceTest extends Specification{

    @Autowired
    ProjectService projectService

    @Autowired
    ProjectRepository projectRepository

    def "should save project to database"() {

        when:
        projectService.create(TestData.PROJECT_DTO)

        then:
        projectRepository.findByName(TestData.PROJECT_DTO.getName()) != null

    }

    def "should edit project name"() {
        projectRepository.getOne(1L) >> new Project()
        given:
        ProjectDto PROJECTDTO = new ProjectDto("projectname", "description", TestData.USER_PROFILE, "icon", TestData.USER_PROFILE_SET)

        when:
        ProjectDto editedProject = projectService.editName("edited", 1L)

        then:
        editedProject.getName() == PROJECTDTO.getName()
    }

    def "should throw exception project not found"() {
        projectRepository.getOne(2L) >> null
        ProjectDto PROJECTDTO = new ProjectDto("projectname", "description", TestData.USER_PROFILE, "icon", TestData.USER_PROFILE_SET)

        when:
        projectService.editName("edited", 2L)

        then:
        ProjectNotFoundException ex = thrown()
        ex.message == "Project with id 2 not found!"
    }
}
