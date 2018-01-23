package common

import scrumweb.dto.ProjectDto
import scrumweb.dto.UserDto
import scrumweb.user.account.domain.UserAccount
import scrumweb.user.profile.domain.UserProfile
import scrumweb.user.project.domain.ProjectMember

class TestData {
    static final UserAccount USER_ACCOUNT = new UserAccount("testUser", "testUser", new UserProfile("testUser", "testUser", null))
    static final UserDto USER_DTO = new UserDto("testUser", "testUser", "test", "test")
    static final UserProfile USER_PROFILE = new UserProfile("Testname", "surname", "Testphoto")
    static final Set<UserProfile> USER_PROFILE_SET = null
    static final Set<ProjectMember> PROJECT_MEMBER_SET = null
    static final ProjectDto PROJECT_DTO = new ProjectDto("projectname", "description", "icon", PROJECT_MEMBER_SET)
}
