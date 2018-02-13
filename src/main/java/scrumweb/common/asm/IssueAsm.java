package scrumweb.common.asm;

import org.springframework.stereotype.Component;
import scrumweb.dto.fieldcontent.FieldContentDto;
import scrumweb.dto.issue.IssueDetailsDto;
import scrumweb.dto.user.UserProfileDto;
import scrumweb.issue.domain.Issue;
import scrumweb.issue.domain.Issue.Priority;
import scrumweb.issue.domain.IssueType;
import scrumweb.issue.fieldcontent.FieldContent;
import scrumweb.user.account.domain.UserAccount;

import java.util.Set;

@Component
public class IssueAsm {

    public Issue createIssueEntityObject(IssueDetailsDto issueDetailsDto, Set<UserAccount> assignees, UserAccount reporter, Set<FieldContent> fieldContents, IssueType issueType) {
        return new Issue(issueDetailsDto.getSummary(), issueDetailsDto.getDescription(), assignees, reporter,
                issueDetailsDto.getEstimateTime(), issueDetailsDto.getRemainingTime(),
                issueDetailsDto.getPriority().equalsIgnoreCase("high") ? Priority.HIGH : Priority.LOW,
                issueType, fieldContents, issueDetailsDto.getCreatedDate(), issueDetailsDto.getLastUpdate());
    }

    public IssueDetailsDto createIssueDetailsDto(Issue issue, Set<UserProfileDto> assignees, UserProfileDto reporter) {
        return new IssueDetailsDto(
                issue.getId(),
                "",
                issue.getSummary(),
                issue.getDescription(),
                assignees,
                reporter,
                issue.getEstimateTime(),
                issue.getRemainingTime(),
                issue.getPriority().toString(),
                issue.getIssueType().getName(),
                issue.getCreatedDate().toString(),
                issue.getLastUpdate().toString()
        );
    }
}