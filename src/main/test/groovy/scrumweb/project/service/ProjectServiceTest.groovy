package scrumweb.project.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import scrumweb.App
import scrumweb.dto.ProjectDto
import scrumweb.user.profile.domain.UserProfile
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
        given:
        UserProfile userProfile = new UserProfile("Testname", "surname", "Testphoto")
        Set<UserProfile> userProfileSet= null
        ProjectDto projectDto = new ProjectDto("projectname", "description", userProfile, "icon", userProfileSet)

        when:
        projectService.create(projectDto)

        then:
        projectRepository.findByName(projectDto.getName()) != null

    }
}
