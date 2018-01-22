package scrumweb.user.account.controller

import common.TestData
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import scrumweb.common.asm.UserAccountAsm
import scrumweb.common.asm.UserProfileAsm
import scrumweb.dto.UserDto
import scrumweb.security.repository.AuthorityRepository
import scrumweb.user.account.repository.UserAccountRepository
import scrumweb.user.account.service.UserAccountService
import scrumweb.user.profile.repository.UserProfileRepository
import spock.lang.Specification

import static infrastructure.MockHttpHelper.httpHelper
import static org.mockito.Matchers.any
import static org.mockito.Mockito.verify
import static org.mockito.MockitoAnnotations.initMocks
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class UserAccountControllerTest extends Specification{

    MockMvc mockMvc
    UserAccountService userAccountService
    UserAccountController userAccountController
    UserAccountRepository userAccountRepositoryMock = Mock()

    @Mock
    UserAccountService userAccountServiceMock
    @Mock
    UserAccountAsm userAccountAsm
    @Mock
    UserProfileRepository userProfileRepository
    @Mock
    UserProfileAsm userProfileAsm
    @Mock
    AuthorityRepository authorityRepository

    private final static API_URL = "user-account"
    private final static JSON_CONTENT = "{username: 'testUser', password: 'testUser', firstname: 'test', lastname: 'test'}"


    def setup() {
        initMocks(this)
    }

    def "should perform user registration request"() {
        setup:
        userAccountController = new UserAccountController(userAccountServiceMock)
        mockMvc = MockMvcBuilders.standaloneSetup(userAccountController).build()

        when:
        def request = mockMvc.perform(httpHelper().requestPost(API_URL + "/save", JSON_CONTENT))

        then:
        request.andExpect(status().isOk())
        verify(userAccountServiceMock, Mockito.times(1)).save(any(UserDto.class))
    }

    def "should throw exception while trying to save user to database"() {
        userAccountRepositoryMock.findByUsername("testUser") >> TestData.USER_ACCOUNT

        setup:
        userAccountService = new UserAccountService(userAccountAsm, userProfileAsm, userAccountRepositoryMock, userProfileRepository, authorityRepository)
        userAccountController = new UserAccountController(userAccountService)
        mockMvc = MockMvcBuilders.standaloneSetup(userAccountController).build()

        when:
        mockMvc.perform(httpHelper().requestPost(API_URL + "/save", JSON_CONTENT))

        then:
        thrown Exception
    }

}
