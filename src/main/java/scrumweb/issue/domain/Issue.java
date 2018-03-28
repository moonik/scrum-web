package scrumweb.issue.domain;

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
import java.util.Set;

@Entity
@Getter @Setter
@NoArgsConstructor
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

    @OneToOne
    @NotNull
    private IssueType issueType;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<FieldContent> fieldContents;

    private LocalDateTime createdDate;

    @UpdateTimestamp
    private Timestamp lastUpdate;

    public enum Priority {
        HIGH, LOW
    }
}
