package scrumweb.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IssueDetailsDto {

    private Long id;
    private String key;
    private String summary;
    private String description;
    private Set<UserProfileDto> assignee;
    private UserProfileDto reporter;
    private String estimateTime;
    private String remainingTime;
    private String priority;
    private String issueType;
    private Set<ProjectFieldDto> fields;

    public IssueDetailsDto(Long id, String key, String summary, String description, Set<UserProfileDto> assignee, UserProfileDto reporter, String estimateTime, String remainingTime, String priority, String issueType, Set<ProjectFieldDto> fields) {
        this.id = id;
        this.key = key;
        this.summary = summary;
        this.description = description;
        this.assignee = assignee;
        this.reporter = reporter;
        this.estimateTime = estimateTime;
        this.remainingTime = remainingTime;
        this.priority = priority;
        this.issueType = issueType;
        this.fields = fields;
    }
}
