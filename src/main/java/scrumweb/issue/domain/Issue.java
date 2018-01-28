package scrumweb.issue.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import scrumweb.project.domain.Project;
import scrumweb.user.account.domain.UserAccount;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Getter @Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Issue {

    @Id @GeneratedValue
    private Long id;

    @NotNull
    private String summary;

    private String description;

    @OneToMany
    private Set<UserAccount> assignee;

    @OneToOne
    @NotNull
    private UserAccount reporter;

    private String estimateTime;

    private String remainingTime;

    @NotNull
    private Priority priority;

    @OneToOne
    @NotNull
    private IssueType issueType;

    public enum Priority {
        HIGH, LOW
    }
}
