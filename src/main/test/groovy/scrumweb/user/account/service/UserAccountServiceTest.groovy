package scrumweb.user.account.service

import common.TestData
import scrumweb.common.asm.UserAccountAsm
import scrumweb.common.asm.UserProfileAsm
import scrumweb.exception.UserAlreadyExistsException
import scrumweb.security.model.Authority
import scrumweb.security.model.AuthorityName
import scrumweb.security.repository.AuthorityRepository
import scrumweb.user.account.repository.UserAccountRepository
import scrumweb.user.profile.repository.UserProfileRepository
import spock.lang.Specification
import spock.lang.Subject

class UserAccountServiceTest extends Specification{

    def userAccountRepository = Mock(UserAccountRepository)
    def userProfileRepository = Mock(UserProfileRepository)
    def authorityRepository = Mock(AuthorityRepository)
    def userAccountAsm = Mock(UserAccountAsm)
    def userProfileAsm = Mock(UserProfileAsm)
    static final USERNAME = "testUser"

    @Subject
    def userAccountService = new UserAccountService(userAccountAsm, userProfileAsm, userAccountRepository, userProfileRepository, authorityRepository)

    def "should save user to database"() {
        authorityRepository.findByName(_) >> new Authority(AuthorityName.ROLE_USER)

        when:
        userAccountService.save(TestData.USER_DTO)

        then:
        1 * userAccountRepository.findByUsername(USERNAME) >> null
        1 * userProfileAsm.makeUserProfile(TestData.USER_DTO) >> TestData.USER_PROFILE
        1 * userProfileRepository.save(TestData.USER_PROFILE)
        1 * userAccountAsm.makeUserAccount(TestData.USER_DTO, TestData.USER_PROFILE) >> TestData.USER_ACCOUNT
        1 * userAccountRepository.save(TestData.USER_ACCOUNT)

    }

    def "should throw exception when trying to save user that already exists in database"() {
        userAccountRepository.findByUsername(_) >> TestData.USER_ACCOUNT

        when:
        userAccountService.save(TestData.USER_DTO)

        then:
        UserAlreadyExistsException ex = thrown()
        ex.message == "User with user name: testUser already exists!"
    }
}
