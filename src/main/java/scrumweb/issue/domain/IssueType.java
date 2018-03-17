package scrumweb.issue.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import scrumweb.project.domain.Project;
import scrumweb.projectfield.domain.ProjectField;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter @Setter
public class IssueType {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<ProjectField> fields;

    @ManyToOne(cascade = CascadeType.ALL)
    private Project project;

    public IssueType(String name, Project project) {
        this.name = name;
        this.project = project;
    }
}
