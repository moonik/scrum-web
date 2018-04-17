package scrumweb.common.asm;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import scrumweb.common.ApplicationConstants;
import scrumweb.dto.issue.IssueCommentDto;
import scrumweb.dto.issue.IssueDetailsDto;
import scrumweb.dto.issue.IssueDto;
import scrumweb.dto.user.UserProfileDto;
import scrumweb.issue.domain.Issue;
import scrumweb.issue.domain.Issue.Priority;
import scrumweb.issue.domain.IssueType;
import scrumweb.issue.fieldcontent.FieldContent;
import scrumweb.user.account.domain.UserAccount;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ApplicationConstants.DATE_FORMAT);
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
                .lastUpdate(new SimpleDateFormat(ApplicationConstants.DATE_FORMAT).format(issue.getLastUpdate()))
                .comments(getComments(issue))
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
                .map(UserProfileAsm::makeUserProfileDto)
                .collect(Collectors.toSet());
    }

    private static UserProfileDto convertReporter(Issue issue) {
        return UserProfileAsm.makeUserProfileDto(issue.getReporter());
    }

    private static Set<String> extractUserNames(Set<UserAccount> assignees) {
        return assignees.stream()
                .map(UserAccount::getUsername)
                .collect(Collectors.toSet());
    }

    private static List<IssueCommentDto> getComments(Issue issue) {
        return issue.getComments().stream()
                .map(c -> IssueCommentAsm.createDtoObject(c, UserProfileAsm.makeUserProfileDto(c.getOwner())))
                .collect(Collectors.toList());
    }
}