package scrumweb.issue.field;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import scrumweb.project.field.ListElement;
import scrumweb.project.field.ProjectField;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ListContent extends FieldContent {

    @ManyToMany
    private Set<ListElement> listElements;

    public ListContent(ProjectField projectField, Set<ListElement> listElements) {
        super(projectField);
        this.listElements = listElements;
    }
}
