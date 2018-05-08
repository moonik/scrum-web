package scrumweb.dto.issue;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import scrumweb.dto.user.UserProfileDto;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IssueCommentDto {

    private Long id;
    private UserProfileDto owner;
    private String content;
    private String createdDate;
}
