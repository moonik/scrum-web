package scrumweb.dto.issue;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import scrumweb.dto.fieldcontent.FieldContentDto;
import scrumweb.dto.projectfield.ProjectFieldDto;
import scrumweb.dto.user.UserProfileDto;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IssueDetailsDto {

    private Long id;
    private String key;
    private String summary;
    private String description;
    private Set<UserProfileDto> assignees;
    private UserProfileDto reporter;
    private String estimateTime;
    private String remainingTime;
    private String priority;
    private String issueType;
    private Set<FieldContentDto> projectFields;
}
