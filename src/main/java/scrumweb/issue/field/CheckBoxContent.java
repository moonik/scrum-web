package scrumweb.issue.field;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import scrumweb.project.field.CheckBox;
import scrumweb.project.field.ProjectField;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
@Getter @Setter
@NoArgsConstructor
public class CheckBoxContent extends FieldContent{

    @OneToMany
    private Set<CheckBox> checkBoxes;

    public CheckBoxContent(ProjectField projectField, Set<CheckBox> checkBoxes) {
        super(projectField);
        this.checkBoxes = checkBoxes;
    }
}
