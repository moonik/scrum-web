package scrumweb.user.account.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import scrumweb.user.profile.domain.UserProfile;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@NoArgsConstructor
@Getter @Setter
public class UserAccount {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Column(unique = true)
    @Size(min = 5, max = 9)
    private String username;

    @NotNull
    @Size(min = 8, max = 16)
    private String password;

    @OneToOne
    @NotNull
    private UserProfile userProfile;

    public UserAccount(String username, String password, UserProfile userProfile) {
        this.username = username;
        this.password = password;
        this.userProfile = userProfile;
    }
}
