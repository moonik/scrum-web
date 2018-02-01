package common

import scrumweb.dto.ProjectDto
import scrumweb.dto.ProjectMemberDto
import scrumweb.dto.UserDto
import scrumweb.project.domain.Project
import scrumweb.user.account.domain.UserAccount
import scrumweb.user.profile.domain.UserProfile
import scrumweb.project.domain.ProjectMember

class TestData {
    static final UserAccount USER_ACCOUNT = new UserAccount("testUser", "testUser", new UserProfile("testUser", "testUser", null), true)
    static final UserDto USER_DTO = new UserDto("testUser", "testUser", "testUser", "testUser")
    static final UserProfile USER_PROFILE = new UserProfile("testUser", "testUser", null)
    static final Set<UserProfile> USER_PROFILE_SET = null
    static final Set<ProjectMemberDto> PROJECT_MEMBER_SET = null
    static final ProjectDto PROJECT_DTO = new ProjectDto("projectname", "description", "icon", PROJECT_MEMBER_SET)
    static final Project PROJECT = new Project("projectname", "description", "icon")
}
