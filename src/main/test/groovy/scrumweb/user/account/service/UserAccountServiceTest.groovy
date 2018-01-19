package scrumweb.user.account.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import scrumweb.App
import scrumweb.dto.UserDto
import scrumweb.exception.UserAlreadyExistsException
import scrumweb.user.account.domain.UserAccount
import scrumweb.user.account.repository.UserAccountRepository
import spock.lang.Specification

@SpringBootTest(classes=[App.class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class UserAccountServiceTest extends Specification{

    @Autowired
    UserAccountService userAccountService

    @Autowired
    UserAccountRepository userAccountRepository

    def "should save user to database"() {
        given:
        UserDto userDto = new UserDto("testUser", "testUser", "test", "test")

        when:
        userAccountService.save(userDto)

        then:
        UserAccount userAccount = userAccountRepository.findByUsername(userDto.getUsername())
        userAccount != null
        userAccount.getUsername() == userDto.getUsername()
        userAccount.getUserProfile() != null
    }

    def "should throw exception when trying to save user that already exists in database"() {
        setup:
        UserDto userDto = new UserDto("testUser", "testUser", "test", "test")
        userAccountService.save(userDto)

        when:
        userAccountService.save(userDto)

        then:
        thrown(UserAlreadyExistsException.class)
    }
}
