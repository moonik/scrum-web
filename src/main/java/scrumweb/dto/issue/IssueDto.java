package scrumweb.dto.issue;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter @Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IssueDto {
    private Long id;
    private String issueKey;
    private String summary;
    private String issueType;
    private String priority;
    private Set<String> assignees;
}
