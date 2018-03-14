package scrumweb.common.asm;

import org.springframework.stereotype.Component;
import scrumweb.dto.issue.IssueCommentDto;
import scrumweb.dto.user.UserProfileDto;
import scrumweb.issue.domain.IssueComment;

@Component
public class IssueCommentAsm {

    public IssueCommentDto fromIssueCommentToIssueCommentDto(IssueComment issueComment, UserProfileDto owner){
        return new IssueCommentDto(owner, issueComment.getContent(), issueComment.getCreatedDate().toString());
    }

}
