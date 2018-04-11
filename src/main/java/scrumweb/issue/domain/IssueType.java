package scrumweb.issue.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import scrumweb.project.domain.Project;
import scrumweb.projectfield.domain.ProjectField;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter @Setter
public class IssueType {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProjectField> fields;

    @ManyToOne
    private Project project;

    private Boolean isDefault;

    public IssueType(String name, Project project, Boolean isDefault) {
        this.name = name;
        this.project = project;
        this.isDefault = isDefault;
    }

    public void edit(String name) {
        this.name = name;
    }
}
