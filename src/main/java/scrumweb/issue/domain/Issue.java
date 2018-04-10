package scrumweb.issue.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
import java.util.List;
import java.util.Set;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class Issue {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String key;

    @NotNull
    private String summary;

    private String description;

    @ManyToMany
    private Set<UserAccount> assignees;

    @OneToOne
    @NotNull
    private UserAccount reporter;

    private String estimateTime;

    private String remainingTime;

    @NotNull
    private Priority priority;

    @ManyToOne
    @NotNull
    private IssueType issueType;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<FieldContent> fieldContents;

    private LocalDateTime createdDate;

    @UpdateTimestamp
    private Timestamp lastUpdate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "issue", orphanRemoval = true)
    private List<IssueComment> comments;

    public enum Priority {
        HIGH, LOW
    }
}
