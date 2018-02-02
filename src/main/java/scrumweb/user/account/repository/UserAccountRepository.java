package scrumweb.user.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import scrumweb.user.account.domain.UserAccount;

import java.util.Set;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    UserAccount findByUsername(String username);

    @Query("select u from UserAccount  u where u.username in :usernames")
    Set<UserAccount> findUsers(Set<String> usernames);
}
