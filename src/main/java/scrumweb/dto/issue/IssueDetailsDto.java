package scrumweb.dto.issue;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import scrumweb.dto.fieldcontent.FieldContentDto;
import scrumweb.dto.fieldcontent.FieldsContentCollector;
import scrumweb.dto.user.UserProfileDto;

import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter @Setter
@Builder
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
    private FieldsContentCollector fieldsContentCollector;
    private Set<FieldContentDto> fieldContents;
    private String createdDate;
    private String lastUpdate;
}
