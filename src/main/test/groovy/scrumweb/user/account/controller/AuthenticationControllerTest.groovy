package scrumweb.user.account.controller

import net.minidev.json.JSONValue
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import scrumweb.App
import scrumweb.dto.UserDto
import scrumweb.dto.UserInformationDto
import scrumweb.security.JwtTokenUtil
import scrumweb.security.JwtUserDetailsServiceImpl
import scrumweb.security.controller.AuthenticationController
import scrumweb.security.model.Authority
import scrumweb.security.model.AuthorityName
import scrumweb.user.account.domain.UserAccount
import scrumweb.user.account.repository.UserAccountRepository;
import scrumweb.user.account.service.UserAccountService
import scrumweb.user.profile.domain.UserProfile
import spock.lang.Specification

import static infrastructure.JsonResultMatcher.jsonResponse
import static infrastructure.MockHttpHelper.httpHelper
import static org.mockito.Matchers.any
import static org.mockito.Matchers.anyCollection
import static org.mockito.Matchers.anyString
import static org.mockito.Mockito.verify
import static org.mockito.Mockito.when
import static org.mockito.MockitoAnnotations.initMocks
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class AuthenticationControllerTest extends Specification{

    MockMvc mockMvc

    UserAccountRepository userAccountRepository = Mock();

    @Mock
    UserAccountService userAccountService;

    AuthenticationController authenticationController;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    private JwtUserDetailsServiceImpl jwtUserDetailsService;

    private final static JSON_CONTENT = "{username: 'testUser', password: 'testUser'}"

    def setup(){
        initMocks(this);
        jwtUserDetailsService = new JwtUserDetailsServiceImpl(userAccountRepository);
        authenticationController = new AuthenticationController(userAccountService, userAccountRepository, authenticationManager, jwtTokenUtil, jwtUserDetailsService);
        mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build()
    }

    def "should perform user authorization"() {
        UserAccount account = new UserAccount("testUser","\$2a\$10\$KG6KiB.Yx0IDwxRdYD9dku9DP3DspOQQ1lxs4o8WNrJs74GNFHtve", any(UserProfile.class))
        account.setAuthorities(Arrays.asList(new Authority(AuthorityName.ROLE_ADMIN)))
        account.setEnabled(true)
        when(userAccountService.getUserInformation(account)).thenReturn(new UserInformationDto("testUser", 1L))
        userAccountRepository.findByUsername("testUser") >> account
//        userAccountService.save(account);

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
