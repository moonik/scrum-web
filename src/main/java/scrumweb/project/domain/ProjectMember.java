package scrumweb.project.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import scrumweb.user.account.domain.UserAccount;
import scrumweb.project.converter.RoleConverter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Arrays;

@Entity
@NoArgsConstructor
@Getter @Setter
@Builder
public class ProjectMember {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @OneToOne
    private UserAccount userAccount;

    @NotNull
    @Convert(converter = RoleConverter.class)
    private Role role;

    public ProjectMember(UserAccount userAccount, Role role) {
        this.userAccount = userAccount;
        this.role = role;
    }

    public enum Role{
        DEVELOPER("developer"), TESTER("tester"), PROJECT_MANAGER("project manager");

        private final String roleString;

        public String getRoleString(){
            return roleString;
        }

        Role(String roleString) {
            this.roleString = roleString;
        }

        public static Role getRole(String roleString){
            return Arrays.stream(Role.values())
                    .filter((Role role) -> role.getRoleString().equals(roleString))
                    .findFirst()
                    .orElse(null);
        }
    }
}

