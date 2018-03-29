package scrumweb.project.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter @Setter
@Builder
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 3, max = 8)
    @Column(unique = true)
    private String key;

    @NotNull
    @Size(min = 5, max = 30)
    private String name;

    @Size(min = 5, max = 300)
    private String description;

    @OneToOne(cascade = CascadeType.ALL)
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

    @OneToMany(cascade = CascadeType.ALL)
    private Set<ProjectMember> requests;

    public Project(String name, String description, String icon, String key) {
        this.name = name;
        this.key = key;
        this.description = description;
        this.icon = icon;
    }
}
