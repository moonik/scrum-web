package scrumweb.common.asm;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import scrumweb.dto.issue.IssueDetailsDto;
import scrumweb.dto.issue.IssueDto;
import scrumweb.dto.issue.IssueTypeDto;
import scrumweb.dto.user.UserProfileDto;
import scrumweb.issue.domain.Issue;
import scrumweb.issue.domain.Issue.Priority;
import scrumweb.issue.domain.IssueType;
import scrumweb.issue.fieldcontent.FieldContent;
import scrumweb.user.account.domain.UserAccount;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class IssueAsm {

    public static Issue createIssueEntityObject(IssueDetailsDto issueDetailsDto, Set<UserAccount> assignees, UserAccount reporter, Set<FieldContent> fieldContents, IssueType issueType) {
        return Issue.builder()
                .summary(issueDetailsDto.getSummary())
                .description(issueDetailsDto.getDescription())
                .assignees(assignees)
                .reporter(reporter)
                .estimateTime(issueDetailsDto.getEstimateTime())
                .remainingTime(issueDetailsDto.getRemainingTime())
                .priority(issueDetailsDto.getPriority().equalsIgnoreCase("high") ? Priority.HIGH : Priority.LOW)
                .issueType(issueType)
                .fieldContents(fieldContents)
                .createdDate(LocalDateTime.now())
                .build();
    }

    public static IssueDetailsDto createIssueDetailsDto(Issue issue) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd HH:mm");
        return IssueDetailsDto.builder()
                .id(issue.getId())
                .key(issue.getKey())
                .summary(issue.getSummary())
                .description(issue.getDescription())
                .assignees(convertAssignees(issue))
                .reporter(convertReporter(issue))
                .estimateTime(issue.getEstimateTime())
                .remainingTime(issue.getRemainingTime())
                .priority(issue.getPriority().name())
                .issueType(issue.getIssueType().getName())
                .createdDate(issue.getCreatedDate().format(formatter))
                .lastUpdate(new SimpleDateFormat("yy-MM-dd HH:mm").format(issue.getLastUpdate()))
                .build();
    }

    public static IssueDto createIssueDto(Issue issue) {
        return IssueDto.builder()
                .id(issue.getId())
                .issueKey(issue.getKey())
                .summary(issue.getSummary())
                .issueType(issue.getIssueType().getName())
                .priority(issue.getPriority().name())
                .assignees(extractUserNames(issue.getAssignees()))
                .build();
    }

    private static Set<UserProfileDto> convertAssignees(Issue issue) {
        return issue.getAssignees().stream()
                .map(userAccount -> UserProfileAsm.makeUserProfileDto(userAccount, userAccount.getUserProfile()))
                .collect(Collectors.toSet());
    }

    private static UserProfileDto convertReporter(Issue issue) {
        return UserProfileAsm.makeUserProfileDto(issue.getReporter(), issue.getReporter().getUserProfile());
    }

    private static Set<String> extractUserNames(Set<UserAccount> assignees) {
        return assignees.stream()
                .map(UserAccount::getUsername)
                .collect(Collectors.toSet());
    }
}