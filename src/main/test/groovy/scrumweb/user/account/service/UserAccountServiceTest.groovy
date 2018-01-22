package scrumweb.user.account.service

import common.TestData
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import scrumweb.App
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

    UserAccountRepository userAccountRepositoryMock = Mock()

    def "should save user to database"() {
        when:
        userAccountService.save(TestData.USER_DTO)

        then:
        UserAccount userAccount = userAccountRepository.findByUsername(TestData.USER_DTO.getUsername())
        userAccount != null
        userAccount.getUserProfile() != null
    }

    def "should throw exception when trying to save user that already exists in database"() {
        userAccountService.userAccountRepository = userAccountRepositoryMock
        userAccountRepositoryMock.findByUsername("testUser") >> TestData.USER_ACCOUNT

        when:
        userAccountService.save(TestData.USER_DTO)

        then:
        UserAlreadyExistsException ex = thrown()
        ex.message == "User with user name: testUser already exists!"
    }
}
