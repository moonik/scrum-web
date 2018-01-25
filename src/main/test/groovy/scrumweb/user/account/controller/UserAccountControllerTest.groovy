package scrumweb.user.account.controller

import common.TestData
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import scrumweb.common.asm.UserAccountAsm
import scrumweb.common.asm.UserProfileAsm
import scrumweb.dto.UserDto
import scrumweb.exception.UserAlreadyExistsException
import scrumweb.security.repository.AuthorityRepository
import scrumweb.user.account.repository.UserAccountRepository
import scrumweb.user.account.service.UserAccountService
import scrumweb.user.profile.repository.UserProfileRepository
import spock.lang.Specification

import static infrastructure.MockHttpHelper.httpHelper
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class UserAccountControllerTest extends Specification{

    def mockMvc
    def userAccountController
    def userAccountService = Mock(UserAccountService)

    private final static API_URL = "user-account"
    private final static JSON_CONTENT = "{username: 'testUser', password: 'testUser', firstname: 'testUser', lastname: 'testUser'}"

    def setup() {
        userAccountController = new UserAccountController(userAccountService)
        mockMvc = MockMvcBuilders.standaloneSetup(userAccountController).build()
    }

    def "should perform user registration request"() {
        when:
        def request = mockMvc.perform(httpHelper().requestPost(API_URL + "/save", JSON_CONTENT))

        then:
        1 * userAccountService.save(_)
        request.andExpect(status().isOk())
    }

    def "should throw exception while trying to save user to database"() {
        when:
        mockMvc.perform(httpHelper().requestPost(API_URL + "/save", JSON_CONTENT))

        then:
        1 * userAccountService.save(_) >> { throw new UserAlreadyExistsException("any") }
        thrown Exception
    }

}
