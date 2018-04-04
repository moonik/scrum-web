package scrumweb.dto.search;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import scrumweb.dto.issue.IssueDto;
import scrumweb.dto.project.ProjectDto;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchResultsDto {
    private List<IssueDto> issues;
    private List<ProjectDto> projects;
}
