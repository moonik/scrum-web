package scrumweb.issue.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import scrumweb.issue.field.FieldContent;
import scrumweb.user.account.domain.UserAccount;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Getter @Setter
@NoArgsConstructor
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

    @OneToMany
    private Set<FieldContent> fieldContents;

    public Issue(String summary, String description, Set<UserAccount> assignee, UserAccount reporter, String estimateTime, String remainingTime, Priority priority, IssueType issueType, Set<FieldContent> fieldContents) {
        this.summary = summary;
        this.description = description;
        this.assignee = assignee;
        this.reporter = reporter;
        this.estimateTime = estimateTime;
        this.remainingTime = remainingTime;
        this.priority = priority;
        this.issueType = issueType;
        this.fieldContents = fieldContents;
    }

    public enum Priority {
        HIGH, LOW
    }
}
