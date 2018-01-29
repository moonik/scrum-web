package scrumweb.issue.field;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import scrumweb.project.field.ProjectField;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter @Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class FieldContent {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private ProjectField projectField;
}
