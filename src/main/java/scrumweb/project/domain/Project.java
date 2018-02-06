package scrumweb.project.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import scrumweb.issue.domain.Issue;
import scrumweb.issue.domain.IssueType;
import scrumweb.projectfield.domain.ProjectField;
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

    @NotNull
    @Size(min = 5, max = 30)
    private String name;

    @Size(min = 5, max = 300)
    private String description;

    @OneToOne
//    @Column(unique = true)
    private UserAccount owner;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Issue> issues;

    private String icon;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<ProjectMember> members;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<ProjectField> projectFields;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<IssueType> issueTypes;

    public Project(String name, String description, String icon) {
        this.name = name;
        this.description = description;
        this.icon = icon;
    }
}
