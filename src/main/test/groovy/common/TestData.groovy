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
    static final Set<InputFieldDto> inputFieldDtos = new HashSet<>(Arrays.asList(new InputFieldDto(1L, "INPUT_FIELD", "test input projectfield", true, "some content", 10, 0)))
    static final Set<CheckBoxDto> checkBoxDtos = new HashSet<>(Arrays.asList(new CheckBoxDto(1L, true, "some value")))
    static final Set<CheckBoxContainerDto> checkBoxContainerDtos = new HashSet<>(Arrays.asList(new CheckBoxContainerDto(2L, "CHECKBOX", "test checkbox container", true, checkBoxDtos)))
    static final Set<ListElementDto> listElementDtos = new HashSet<>(Arrays.asList(new ListElementDto(1L, true, "some value")))
    static final Set<ListElementsContainerDto> listElementsContainerDtos = new HashSet<>(Arrays.asList(new ListElementsContainerDto(3L, "LIST", "test list", true, listElementDtos)))
    static final Set<RadioButtonDto> radioButtonDtos = new HashSet<>(Arrays.asList(new RadioButtonDto(1L, "some value", true)))
    static final Set<RadioButtonContainerDto> radioButtonContainerDtos = new HashSet<>(Arrays.asList(new RadioButtonContainerDto(4L, "RADIO_BUTTON", "some name", true, radioButtonDtos)))
    static final Set<TextAreaDto> textAreaDtos = new HashSet<>(Arrays.asList(new TextAreaDto(5L, "TEXT_AREA", "some name", true, "some content", 10, 0)))
    static final FieldsDto fieldsDto = new FieldsDto(inputFieldDtos, checkBoxContainerDtos, listElementsContainerDtos, radioButtonContainerDtos, textAreaDtos)
    static final Set<CheckBox> checkBoxes = new HashSet<>(Arrays.asList(new CheckBox("some value")))
    static final Set<CheckBoxContainer> checkBoxContainer = new HashSet<>(Arrays.asList(new CheckBoxContainer(ProjectField.FieldType.CHECKBOX, "test checkbox", true, checkBoxes)))
    static final Set<RadioButton> radioButtons = new HashSet<>(Arrays.asList(new RadioButton("some value")))
    static final Set<RadioButtonContainer> radioButtonContainers = new HashSet<>(Arrays.asList(new RadioButtonContainer(ProjectField.FieldType.RADIO_BUTTON, "test radio", true,  radioButtons)))
    static final Set<InputField> inputFields = new HashSet<>(Arrays.asList(new InputField(ProjectField.FieldType.INPUT_FIELD, "input projectfield", true, 0, 10)))
    static final Set<ListElement> listElements = new HashSet<>(Arrays.asList(new ListElement("some value")))
    static final Set<ListElementsContainer> listElementsContainer = new HashSet<>(Arrays.asList(new ListElementsContainer(ProjectField.FieldType.LIST, "some name", true, listElements)))
    static final Set<TextArea> textAreas = new HashSet<>(Arrays.asList(new TextArea(ProjectField.FieldType.TEXT_AREA, "some name", true, 0, 10)))

}
