package scrumweb.project.service

import common.TestData
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import scrumweb.App
import scrumweb.project.repository.ProjectRepository
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
}
