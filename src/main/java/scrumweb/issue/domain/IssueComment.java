package scrumweb.issue.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import scrumweb.user.account.domain.UserAccount;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class IssueComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private UserAccount owner;

    private String content;

    private LocalDateTime createdDate;

    @ManyToOne(fetch = FetchType.EAGER)
    private Issue issue;

    public IssueComment(UserAccount owner, String content, LocalDateTime createdDate, Issue issue) {
        this.owner = owner;
        this.content = content;
        this.createdDate = createdDate;
        this.issue = issue;
    }
}
