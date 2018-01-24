package scrumweb.user.project.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import scrumweb.user.account.domain.UserAccount;
import scrumweb.user.profile.domain.UserProfile;
import scrumweb.user.project.converter.RoleConverter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Arrays;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class ProjectMember {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "projectMember_seq")
    @SequenceGenerator(name = "projectMember_seq", sequenceName = "projectMember_seq", allocationSize = 1)
    Long Id;

    @NotNull
    @OneToOne
    UserAccount userAccount;

    @NotNull
    @Convert(converter = RoleConverter.class)
    Role role;

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
            return Arrays.stream(Role.values()).filter((Role role) ->
                                                        role.getRoleString().equals(roleString)).findFirst().orElse(null);
        }
    }

}

