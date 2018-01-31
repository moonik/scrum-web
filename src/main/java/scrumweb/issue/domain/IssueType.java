package scrumweb.issue.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import scrumweb.project.domain.Project;
import scrumweb.project.field.ProjectField;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter @Setter
public class IssueType {

    @Id @GeneratedValue
    private Long id;

    @NotNull
    @Column(unique = true)
    private String name;

    @ManyToMany
    private Set<ProjectField> fields;

    @ManyToOne
    private Project project;

    public IssueType(String name, Set<ProjectField> fields, Project project) {
        this.name = name;
        this.fields = fields;
        this.project = project;
    }
}