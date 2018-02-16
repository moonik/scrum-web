package scrumweb.user.account.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import scrumweb.project.domain.Project;
import scrumweb.security.domain.Authority;
import scrumweb.user.profile.domain.UserProfile;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter @Setter
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    @Size(min = 5, max = 9)
    private String username;

    @NotNull
    @Size(min = 5)
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @NotNull
    private UserProfile userProfile;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "USER_AUTHORITY",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "AUTHORITY_ID", referencedColumnName = "ID")})
    private List<Authority> authorities;

    @Column(name = "ENABLED")
    @NotNull
    private Boolean enabled;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Project> projects;

    public UserAccount(String username, String password, UserProfile userProfile, Boolean enabled) {
        this.username = username;
        this.password = password;
        this.userProfile = userProfile;
        this.enabled = enabled;
    }
}
