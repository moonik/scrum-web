package scrumweb.security

import common.TestData
import org.mockito.Mock
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import scrumweb.common.SecurityContextService
import scrumweb.security.controller.AuthenticationController
import scrumweb.user.account.repository.UserAccountRepository
import scrumweb.user.account.service.UserAccountService
import spock.lang.Specification

import static infrastructure.JsonResultMatcher.jsonResponse
import static infrastructure.MockHttpHelper.httpHelper
import static org.mockito.Mockito.mock
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class SecurityTest extends Specification{

    SecurityContextService securityContextService
    MockMvc mockMvc

    private final static JSON_CONTENT = "{username: 'testUser', password: 'testUser'}"

    def setup() {
        Authentication authentication = Mock(Authentication.class)
        SecurityContext securityContext = Mock(SecurityContext.class)
        UserDetails userDetails = Mock(UserDetails.class)
        JwtTokenUtil jwtTokenUtil = mock(JwtTokenUtil.class)
        UserAccountService userAccountService = mock(UserAccountService.class)
        JwtUserDetailsServiceImpl jwtUserDetailsService = mock(JwtUserDetailsServiceImpl.class)
        UserAccountRepository userAccountRepository = Mock(UserAccountRepository.class)
        AuthenticationManager authenticationManager = Mock(AuthenticationManager.class)

        securityContext.getAuthentication() >> authentication
        authentication.getName() >> "testUser"
        jwtUserDetailsService.loadUserByUsername("testUser") >> userDetails
        userAccountRepository.findByUsername("testUser") >> TestData.USER_ACCOUNT
        userDetails.getUsername() >> "testUser"

        SecurityContextHolder.setContext(securityContext)
        securityContextService = new SecurityContextService(userAccountRepository)

        AuthenticationController authenticationController = new AuthenticationController(userAccountService, userAccountRepository, authenticationManager, jwtTokenUtil, jwtUserDetailsService)
        mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build()
    }

    def "should return logged user account"() {
        expect:
        securityContextService.getCurrentUserAccount() != null
        securityContextService.getCurrentUserAccount().getUsername() == "testUser"
    }

    def "should perform login request"() {
        when:
        def request = mockMvc.perform(httpHelper().requestPost("/auth", JSON_CONTENT))

        then:
        request.andExpect(status().isOk())
                .andExpect(jsonResponse().notEmpty())
    }
}
