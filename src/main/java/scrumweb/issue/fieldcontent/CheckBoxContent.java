package scrumweb.issue.fieldcontent;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import scrumweb.projectfield.domain.CheckBox;
import scrumweb.projectfield.domain.ProjectField;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Set;

@Entity
@Getter @Setter
@NoArgsConstructor
public class CheckBoxContent extends FieldContent{

    @ManyToMany
    private Set<CheckBox> checkBoxes;

    public CheckBoxContent(ProjectField projectField, Set<CheckBox> checkBoxes) {
        super(projectField);
        this.checkBoxes = checkBoxes;
    }
}
