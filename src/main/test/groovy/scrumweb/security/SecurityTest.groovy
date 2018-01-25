package scrumweb.security

import common.TestData
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
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

import javax.naming.AuthenticationException

import static infrastructure.JsonResultMatcher.jsonResponse
import static infrastructure.MockHttpHelper.httpHelper
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class SecurityTest extends Specification{

    def securityContextService
    def mockMvc

    def authentication = Mock(Authentication)
    def securityContext = Mock(SecurityContext)
    def userDetails = Mock(UserDetails)
    def jwtTokenUtil = Mock(JwtTokenUtil)
    def userAccountService = Mock(UserAccountService)
    def jwtUserDetailsService = Mock(JwtUserDetailsServiceImpl)
    def userAccountRepository = Mock(UserAccountRepository)
    def authenticationManager = Mock(AuthenticationManager)

    private final static String USERNAME = "testUser"
    private final static String PASSWORD = "testUser"
    private final static String JSON_CONTENT = "{username: " + USERNAME + ", password: " + PASSWORD + "}"

    def setup() {
        securityContext.getAuthentication() >> authentication
        authentication.getName() >> USERNAME
        jwtUserDetailsService.loadUserByUsername(USERNAME) >> userDetails
        userAccountRepository.findByUsername(USERNAME) >> TestData.USER_ACCOUNT
        userDetails.getUsername() >> USERNAME

        SecurityContextHolder.setContext(securityContext)
        securityContextService = new SecurityContextService(userAccountRepository)

        AuthenticationController authenticationController = new AuthenticationController(userAccountService, userAccountRepository, authenticationManager, jwtTokenUtil, jwtUserDetailsService)
        mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build()
    }

    def "should return logged user account"() {
        expect:
        securityContextService.getCurrentUserAccount() != null
        securityContextService.getCurrentUserAccount().getUsername() == USERNAME
    }

    def "should perform log in request"() {
        when:
        def request = mockMvc.perform(httpHelper().requestPost("/auth", JSON_CONTENT))

        then:
        request.andExpect(status().isOk())
                .andExpect(jsonResponse().notEmpty())
    }

    def "should fail on log in request"() {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(USERNAME, PASSWORD)) >> AuthenticationException

        when:
        mockMvc.perform(httpHelper().requestPost("/auth", JSON_CONTENT))

        then:
        thrown Exception
    }
}
