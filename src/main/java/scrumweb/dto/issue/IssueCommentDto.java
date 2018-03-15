package scrumweb.dto.issue;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import scrumweb.dto.user.UserProfileDto;

@NoArgsConstructor
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IssueCommentDto {

    private UserProfileDto owner;
    private String content;
    private String createdDate;

    public IssueCommentDto(UserProfileDto owner, String content, String createdDate) {
        this.owner = owner;
        this.content = content;
        this.createdDate = createdDate;
    }
}
