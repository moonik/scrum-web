package scrumweb.issue.field;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import scrumweb.project.field.CheckBox;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
@Getter @Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class CheckBoxContent extends FieldContent{

    @OneToMany
    private Set<CheckBox> checkBoxes;
}
