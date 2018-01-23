package scrumweb.security

import common.TestData
import org.mockito.Mockito
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import scrumweb.common.SecurityContextService
import scrumweb.user.account.repository.UserAccountRepository
import spock.lang.Specification

class SecurityTest extends Specification{

    UserAccountRepository userAccountRepository = Mock()
    SecurityContextService securityContextService

    def setup() {
        Authentication authentication = Mock(Authentication.class)
        SecurityContext securityContext = Mock(SecurityContext.class)

        securityContext.getAuthentication() >> authentication
        authentication.getName() >> "testUser"

        SecurityContextHolder.setContext(securityContext)

        securityContextService = new SecurityContextService(userAccountRepository)
        userAccountRepository.findByUsername("testUser") >> TestData.USER_ACCOUNT
    }

    def "should return logged user account"() {
        expect:
        securityContextService.getCurrentUserAccount() != null
        securityContextService.getCurrentUserAccount().getUsername() == "testUser"
    }

}
