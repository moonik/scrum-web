package scrumweb.user.account.controller

import net.minidev.json.JSONValue
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.test.web.servlet.MockMvc
import scrumweb.App
import scrumweb.dto.UserDto
import scrumweb.security.JwtTokenUtil
import scrumweb.security.JwtUserDetailsServiceImpl
import scrumweb.security.controller.AuthenticationController
import scrumweb.user.account.domain.UserAccount
import scrumweb.user.account.repository.UserAccountRepository;
import scrumweb.user.account.service.UserAccountService
import scrumweb.user.profile.domain.UserProfile
import spock.lang.Specification

import static infrastructure.JsonResultMatcher.jsonResponse
import static infrastructure.MockHttpHelper.httpHelper
import static org.mockito.Matchers.any
import static org.mockito.Matchers.anyString
import static org.mockito.Mockito.verify
import static org.mockito.Mockito.when
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = AuthenticationController, secure = false)
class AuthenticationControllerTest extends Specification{

    @Autowired
    MockMvc mockMvc

    @MockBean
    UserAccountRepository userAccountRepository

    @Autowired
    UserAccountService userAccountService

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    @MockBean
    private JwtUserDetailsServiceImpl jwtUserDetailsService;

    private final static JSON_CONTENT = "{username: 'testUser', password: 'testUser'}"

    def "should perform user authorization"() {

        UserAccount account = new UserAccount("testUser","\$2a\$10\$KG6KiB.Yx0IDwxRdYD9dku9DP3DspOQQ1lxs4o8WNrJs74GNFHtve", any(UserProfile.class))
//        when(userAccountRepository.findByUsername("")).thenReturn(account)
        userAccountService.save(account);

        when:
//        println "test"
        def request = mockMvc.perform(httpHelper().requestPost( "/auth", JSON_CONTENT))
//
        then:
        println "test"
        request
                .andExpect(status().isOk())
        .andExpect(jsonResponse().notEmpty())
    }

}
