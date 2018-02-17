package scrumweb.issue.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;
import scrumweb.issue.fieldcontent.FieldContent;
import scrumweb.user.account.domain.UserAccount;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Issue {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String summary;

    private String description;

    @OneToMany
    private Set<UserAccount> assignees;

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

    @OneToMany(cascade = CascadeType.ALL)
    private Set<FieldContent> fieldContents;

    private LocalDateTime createdDate;

    @UpdateTimestamp
    private Timestamp    lastUpdate;

    public Issue(String summary, String description, Set<UserAccount> assignees, UserAccount reporter, String estimateTime, String remainingTime,
                 Priority priority, IssueType issueType, Set<FieldContent> fieldContents, String createdDate) {
        this.summary = summary;
        this.description = description;
        this.assignees = assignees;
        this.reporter = reporter;
        this.estimateTime = estimateTime;
        this.remainingTime = remainingTime;
        this.priority = priority;
        this.issueType = issueType;
        this.fieldContents = fieldContents;
        this.createdDate = LocalDateTime.parse(createdDate);
    }

    public enum Priority {
        HIGH, LOW
    }
}
