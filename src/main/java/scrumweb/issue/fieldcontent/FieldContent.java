package scrumweb.issue.fieldcontent;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import scrumweb.projectfield.domain.ProjectField;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter @Setter
@NoArgsConstructor
public class FieldContent {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private ProjectField projectField;

    public FieldContent(ProjectField projectField) {
        this.projectField = projectField;
    }
}
