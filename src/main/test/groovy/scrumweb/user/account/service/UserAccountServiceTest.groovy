package scrumweb.user.account.service

import org.mockito.Mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.transaction.annotation.Transactional
import scrumweb.common.asm.UserAccountAsm
import scrumweb.common.asm.UserProfileAsm
import scrumweb.dto.UserDto
import scrumweb.user.account.domain.UserAccount
import scrumweb.user.account.repository.UserAccountRepository
import scrumweb.user.profile.domain.UserProfile
import scrumweb.user.profile.repository.UserProfileRepository
import spock.lang.Specification
import spock.lang.Subject

import static org.mockito.MockitoAnnotations.initMocks

@SpringBootTest
@WebAppConfiguration
@Transactional
class UserAccountServiceTest extends Specification{

    @Subject
    UserAccountService userAccountService = new UserAccountService()

    @Mock
    UserProfileAsm userProfileAsm
    @Mock
    UserAccountAsm userAccountAsm

    def setup() {
        initMocks(this)
        userAccountService.userAccountRepository = Mock(UserAccountRepository)
        userAccountService.userProfileRepository = Mock(UserProfileRepository)
        userAccountService.userAccountAsm = userAccountAsm
        userAccountService.userProfileAsm = userProfileAsm
    }

    def "should save user to database"() {
        setup:
        UserDto userDto = new UserDto("ololo", "test", "lol", "lolski")
        UserAccount expectedResult = new UserAccount("ololo", "test", new UserProfile("lol", "lolski", null))
        userAccountService.userAccountRepository.save(expectedResult) >> expectedResult

        expect:
        userAccountService.save(userDto) == expectedResult
    }
}
