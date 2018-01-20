package common

import scrumweb.dto.UserDto
import scrumweb.user.account.domain.UserAccount
import scrumweb.user.profile.domain.UserProfile

class TestData {
    static final UserAccount userAccount = new UserAccount("testUser", "testUser", new UserProfile("testUser", "testUser", null))
    static final UserDto userDto = new UserDto("testUser", "testUser", "test", "test")
}
