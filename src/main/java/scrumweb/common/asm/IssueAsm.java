package scrumweb.common.asm;

import org.springframework.stereotype.Component;
import scrumweb.dto.IssueDto;
import scrumweb.issue.domain.Issue;
import scrumweb.issue.domain.IssueType;
import scrumweb.issue.field.FieldContent;
import scrumweb.user.account.domain.UserAccount;

import java.util.Set;

@Component
public class IssueAsm {

    public static Issue makeIssue(IssueDto issueDto, Set<UserAccount> assignee, UserAccount reporter, Set<FieldContent> fieldContents, IssueType issueType) {
        return new Issue(issueDto.getSummary(), issueDto.getDescription(), assignee, reporter,
                issueDto.getEstimateTime(), issueDto.getRemainingTime(),
                issueDto.getPriority().equalsIgnoreCase("high") ? Issue.Priority.HIGH : Issue.Priority.LOW,
                issueType, fieldContents);
    }
}