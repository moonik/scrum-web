package scrumweb.user.account.controller

import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import scrumweb.dto.UserDto
import scrumweb.user.account.service.UserAccountService
import spock.lang.Specification

import static infrastructure.MockHttpHelper.httpHelper
import static org.mockito.Matchers.any
import static org.mockito.Mockito.verify
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = UserAccountController, secure = false)
class UserAccountControllerTest extends Specification{

    @Autowired
    MockMvc mockMvc

    @MockBean
    UserAccountService userAccountService

    private final static API_URL = "user-account"
    private final static JSON_CONTENT = "{username: 'testUser', password: 'testUser', firstname: 'test', lastname: 'test'}"

    def "should perform user registration request"() {
        when:
        def request = mockMvc.perform(httpHelper().requestPost(API_URL + "/save", JSON_CONTENT))

        then:
        request
                .andExpect(status().isOk())
        verify(userAccountService, Mockito.times(1)).save(any(UserDto.class))
    }

}
