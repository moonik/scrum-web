package scrumweb.project.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import scrumweb.user.account.domain.UserAccount;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Project {

    @Id
    @GeneratedValue
    private Long id;

    @Size(min = 3, max = 8)
    @Column(unique = true)
    private String key;

    @NotNull
    @Size(min = 5, max = 30)
    private String name;

    @Size(min = 5, max = 300)
    private String description;

    @OneToOne
    private UserAccount owner;

    private String icon;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<ProjectMember> members;

    public Project(String name, String description, String icon, String key) {
        this.name = name;
        this.key = key;
        this.description = description;
        this.icon = icon;
    }
}
