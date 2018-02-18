package scrumweb.dto.project;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import scrumweb.dto.issue.IssueDto;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class ProjectDetailsDto {

    private ProjectDto projectDto;
    private Set<IssueDto> issues;
    //TODO add boards
}
