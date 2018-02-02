package scrumweb.common.asm;

import org.springframework.stereotype.Component;
import scrumweb.dto.issue.IssueDetailsDto;
import scrumweb.dto.projectfield.ProjectFieldDto;
import scrumweb.dto.user.UserProfileDto;
import scrumweb.issue.domain.Issue;
import scrumweb.issue.domain.Issue.Priority;
import scrumweb.issue.domain.IssueType;
import scrumweb.issue.field.FieldContent;
import scrumweb.user.account.domain.UserAccount;

import java.util.Set;

@Component
public class IssueAsm {

    public Issue createIssueEntityObject(IssueDetailsDto issueDetailsDto, Set<UserAccount> assignee, UserAccount reporter, Set<FieldContent> fieldContents, IssueType issueType) {
        return new Issue(issueDetailsDto.getSummary(), issueDetailsDto.getDescription(), assignee, reporter,
                issueDetailsDto.getEstimateTime(), issueDetailsDto.getRemainingTime(),
                issueDetailsDto.getPriority().equalsIgnoreCase("high") ? Priority.HIGH : Priority.LOW,
                issueType, fieldContents);
    }

    public IssueDetailsDto createIssueDetailsDto(Issue issue, Set<UserProfileDto> assignees, UserProfileDto reporter, Set<ProjectFieldDto> fields) {
        return new IssueDetailsDto(
                issue.getId(), "",
                issue.getSummary(),
                issue.getDescription(),
                assignees, reporter,
                issue.getEstimateTime(),
                issue.getRemainingTime(),
                issue.getPriority().toString(),
                issue.getIssueType().getName(),
                fields
        );
    }
}