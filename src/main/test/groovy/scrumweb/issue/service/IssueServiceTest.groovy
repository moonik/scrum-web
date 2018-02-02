//package scrumweb.issue.service
//
//import common.TestData
//import scrumweb.common.SecurityContextService
//import scrumweb.common.asm.FieldAsm
//import scrumweb.common.asm.IssueAsm
//import scrumweb.common.asm.UserProfileAsm
//import scrumweb.dto.FieldsDto
//import scrumweb.issue.domain.IssueType
//import scrumweb.issue.repository.IssueRepository
//import scrumweb.issue.repository.IssueTypeRepository
//import scrumweb.issue.repository.FieldContentRepository
//import scrumweb.issue.service.IssueService
//import scrumweb.project.field.CheckBoxContainer
//import scrumweb.project.field.InputField
//import scrumweb.project.field.ListElementsContainer
//import scrumweb.project.field.ProjectField
//import scrumweb.project.field.RadioButtonContainer
//import scrumweb.project.field.TextArea
//import scrumweb.project.repository.ProjectFieldRepository
//import scrumweb.project.repository.ProjectRepository
//import scrumweb.user.account.repository.UserAccountRepository
//import spock.lang.Specification
//import spock.lang.Subject
//
//class IssueServiceTest extends Specification {
//
//    def issueAsm = Mock(IssueAsm)
//    def fieldAsm = Mock(FieldAsm)
//    def issueRepository = Mock(IssueRepository)
//    def userAccountRepository = Mock(UserAccountRepository)
//    def securityContextService = Mock(SecurityContextService)
//    def projectFieldRepository = Mock(ProjectFieldRepository)
//    def issueTypeRepository = Mock(IssueTypeRepository)
//    def userProfileAsm = Mock(UserProfileAsm)
//    def projectRepository = Mock(ProjectRepository)
//    def fieldContentRepository = Mock(FieldContentRepository)
//
//    @Subject
//    def issueService = new IssueService(issueAsm, fieldAsm, issueRepository, userAccountRepository, securityContextService, projectFieldRepository,
//            issueTypeRepository, userProfileAsm, projectRepository, fieldContentRepository)
//
//    def setup() {
//        securityContextService.getCurrentUserAccount() >> TestData.USER_ACCOUNT
//        securityContextService.getCurrentUserProfile() >> TestData.USER_PROFILE
//    }
//
//    def "should create fields"() {
//        given:
//        FieldsDto fieldsDto = TestData.fieldsDto
//        Set<InputField> inputFields = TestData.inputFields
//        Set<CheckBoxContainer> checkBoxContainers = TestData.checkBoxContainer
//        Set<RadioButtonContainer> radioButtonContainers = TestData.radioButtonContainers
//        Set<ListElementsContainer> listElementsContainers = TestData.listElementsContainer
//        Set<TextArea> textAreas = TestData.textAreas
//        Set<ProjectField> projectFields1 = new HashSet<>()
//        def issueTypeFromDb = new IssueType("TASK", projectFields1, TestData.PROJECT)
//        def issueType = "TASK"
//
//        and:
//        Set<ProjectField> projectFields = new HashSet<>()
//        projectFields.addAll(inputFields)
//        projectFields.addAll(checkBoxContainers)
//        projectFields.addAll(radioButtonContainers)
//        projectFields.addAll(listElementsContainers)
//        projectFields.addAll(textAreas)
//
//        issueService.getCheckBoxes(fieldsDto.getCheckBoxContainerDtos()) >> checkBoxContainers
//        issueService.getRadioButtons(fieldsDto.getRadioButtonContainerDtos()) >> radioButtonContainers
//        issueService.getInputFields(fieldsDto.getInputFieldDtos()) >> inputFields
//        issueService.getListContainer(fieldsDto.getListElementsContainerDtos()) >> listElementsContainers
//        issueService.getTextArea(fieldsDto.getTextAreaDtos()) >> textAreas
//        issueTypeRepository.findByName(issueType) >> issueTypeFromDb
//
//        when:
//        issueService.createFields(fieldsDto, issueType)
//
//        then:
//        //1 * issueService.getCheckBoxes(fieldsDto.getCheckBoxContainerDtos())
//        1 * fieldAsm.createCheckBoxContainer(_)
//        1 * issueService.getRadioButtons(fieldsDto.getRadioButtonContainerDtos())
//        1 * issueService.getListContainer(fieldsDto.getListElementsContainerDtos())
//        1 * issueService.getInputFields(fieldsDto.getInputFieldDtos())
//        1 * issueService.getTextArea(fieldsDto.getTextAreaDtos())
//        1 * projectFieldRepository.save(projectFields)
//        1 * issueTypeRepository.save(issueTypeFromDb)
//    }
//}
