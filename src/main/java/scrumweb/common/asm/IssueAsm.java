package scrumweb.common.asm;

import org.springframework.stereotype.Component;
import scrumweb.dto.FieldsDto;
import scrumweb.dto.IssueDetailsDto;
import scrumweb.dto.ProjectFieldDto;
import scrumweb.dto.UserProfileDto;
import scrumweb.issue.domain.Issue;
import scrumweb.issue.domain.IssueType;
import scrumweb.issue.field.FieldContent;
import scrumweb.project.domain.Project;
import scrumweb.user.account.domain.UserAccount;
import scrumweb.user.profile.domain.UserProfile;

import java.util.Set;

@Component
public class IssueAsm {

    public Issue makeIssue(IssueDetailsDto issueDetailsDto, Set<UserAccount> assignee, UserAccount reporter, Set<FieldContent> fieldContents, IssueType issueType) {
        return new Issue(issueDetailsDto.getSummary(), issueDetailsDto.getDescription(), assignee, reporter,
                issueDetailsDto.getEstimateTime(), issueDetailsDto.getRemainingTime(),
                issueDetailsDto.getPriority().equalsIgnoreCase("high") ? Issue.Priority.HIGH : Issue.Priority.LOW,
                issueType, fieldContents);
    }

    public IssueDetailsDto createIssueDetailsDto(Issue issue, Set<UserProfileDto> assignees, UserProfileDto reporter, FieldsDto fields) {
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