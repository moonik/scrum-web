package scrumweb.common.asm;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import scrumweb.dto.issue.IssueCommentDto;
import scrumweb.dto.user.UserProfileDto;
import scrumweb.issue.domain.Issue;
import scrumweb.issue.domain.IssueComment;
import scrumweb.user.account.domain.UserAccount;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IssueCommentAsm {

    public static IssueCommentDto createDtoObject(IssueComment issueComment, UserProfileDto owner){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd HH:mm");
        return new IssueCommentDto(issueComment.getId(), owner, issueComment.getContent(), issueComment.getCreatedDate().format(formatter));
    }

    public static IssueComment createEntityObject(IssueCommentDto issueCommentDto, UserAccount commentOwner, Issue issue) {
        return new IssueComment(commentOwner, issueCommentDto.getContent(), LocalDateTime.now(), issue);
    }
}
