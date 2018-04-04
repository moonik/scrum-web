package scrumweb.dto.project;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import scrumweb.dto.issue.IssueDto;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter @Setter
@Builder
public class ProjectDetailsDto {
    private ProjectDto projectDto;
    private List<IssueDto> issues;
    //TODO add boards
}
