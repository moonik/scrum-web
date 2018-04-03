package scrumweb.common.asm;

import org.springframework.stereotype.Component;
import scrumweb.dto.issue.IssueCommentDto;
import scrumweb.dto.user.UserProfileDto;
import scrumweb.issue.domain.IssueComment;

import java.time.format.DateTimeFormatter;

@Component
public class IssueCommentAsm {

    public static IssueCommentDto createDtoObject(IssueComment issueComment, UserProfileDto owner){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd HH:mm");
        return new IssueCommentDto(issueComment.getId(), owner, issueComment.getContent(), issueComment.getCreatedDate().format(formatter));
    }
}
