package common

import scrumweb.dto.projectfield.CheckBoxContainerDto
import scrumweb.dto.projectfield.CheckBoxDto

import scrumweb.dto.projectfield.InputFieldDto
import scrumweb.dto.projectfield.ListElementDto
import scrumweb.dto.projectfield.ListElementsContainerDto
import scrumweb.dto.project.ProjectDto
import scrumweb.dto.project.ProjectMemberDto
import scrumweb.dto.projectfield.RadioButtonContainerDto
import scrumweb.dto.projectfield.RadioButtonDto
import scrumweb.dto.projectfield.TextAreaDto
import scrumweb.dto.user.UserDto
import scrumweb.project.domain.Project
import scrumweb.projectfield.domain.CheckBox
import scrumweb.projectfield.domain.CheckBoxContainer
import scrumweb.projectfield.domain.InputField
import scrumweb.projectfield.domain.ListElement
import scrumweb.projectfield.domain.ListElementsContainer
import scrumweb.projectfield.domain.ProjectField
import scrumweb.projectfield.domain.RadioButton
import scrumweb.projectfield.domain.RadioButtonContainer
import scrumweb.projectfield.domain.TextArea
import scrumweb.user.account.domain.UserAccount
import scrumweb.user.profile.domain.UserProfile

class TestData {
    static final UserAccount USER_ACCOUNT = new UserAccount("testUser", "testUser", new UserProfile("testUser", "testUser", null), true)
    static final UserDto USER_DTO = new UserDto("testUser", "testUser", "testUser", "testUser")
    static final UserProfile USER_PROFILE = new UserProfile("testUser", "testUser", null)
    static final Set<UserProfile> USER_PROFILE_SET = null
    static final Set<ProjectMemberDto> PROJECT_MEMBER_SET = null
    static final ProjectDto PROJECT_DTO = new ProjectDto("projectname", "description", "icon", PROJECT_MEMBER_SET)
    static final Project PROJECT = new Project("projectname", "description", "icon")
}
