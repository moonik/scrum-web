package scrumweb.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import scrumweb.user.account.domain.UserAccount;
import scrumweb.user.account.repository.UserAccountRepository;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private UserAccountRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount userAccount = userRepository.findByUsername(username);

        if(userAccount == null) {
            throw new UsernameNotFoundException(String.format("No userAccount found with username: '%s'.", username));
        } else {
            return JwtUserFactory.create(userAccount);
        }
    }
}
