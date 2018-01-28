package scrumweb.issue.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import scrumweb.project.field.ProjectField;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Getter @Setter
public class IssueType {

    @Id @GeneratedValue
    private Long id;

    @NotNull
    private String name;

    @ManyToMany
    private Set<ProjectField> fields;
}
