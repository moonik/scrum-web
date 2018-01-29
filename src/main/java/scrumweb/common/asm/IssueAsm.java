package scrumweb.common.asm;

import org.springframework.stereotype.Component;
import scrumweb.dto.IssueDetailsDto;
import scrumweb.issue.domain.Issue;
import scrumweb.issue.domain.IssueType;
import scrumweb.issue.field.FieldContent;
import scrumweb.user.account.domain.UserAccount;

import java.util.Set;

@Component
public class IssueAsm {

    public Issue makeIssue(IssueDetailsDto issueDetailsDto, Set<UserAccount> assignee, UserAccount reporter, Set<FieldContent> fieldContents, IssueType issueType) {
        return new Issue(issueDetailsDto.getSummary(), issueDetailsDto.getDescription(), assignee, reporter,
                issueDetailsDto.getEstimateTime(), issueDetailsDto.getRemainingTime(),
                issueDetailsDto.getPriority().equalsIgnoreCase("high") ? Issue.Priority.HIGH : Issue.Priority.LOW,
                issueType, fieldContents);
    }
}