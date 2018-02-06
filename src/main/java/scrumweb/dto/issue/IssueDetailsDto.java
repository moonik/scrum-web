package scrumweb.dto.issue;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import scrumweb.dto.fieldcontent.FieldContentDto;
import scrumweb.dto.fieldcontent.FieldsContentCollector;
import scrumweb.dto.projectfield.ProjectFieldDto;
import scrumweb.dto.user.UserProfileDto;

import java.util.Set;

@NoArgsConstructor
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
    private FieldsContentCollector projectFields;
    private Set<FieldContentDto> fieldContents;

    public IssueDetailsDto(Long id, String key, String summary, String description, Set<UserProfileDto> assignees, UserProfileDto reporter, String estimateTime, String remainingTime, String priority, String issueType) {
        this.id = id;
        this.key = key;
        this.summary = summary;
        this.description = description;
        this.assignees = assignees;
        this.reporter = reporter;
        this.estimateTime = estimateTime;
        this.remainingTime = remainingTime;
        this.priority = priority;
        this.issueType = issueType;
    }
}
