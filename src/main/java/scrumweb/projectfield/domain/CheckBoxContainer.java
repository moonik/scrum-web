package scrumweb.projectfield.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter @Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class CheckBoxContainer extends ProjectField{

    @OneToMany(cascade = CascadeType.ALL)
    private Set<CheckBox> checkBoxes;

    public CheckBoxContainer(FieldType fieldType, String name, Boolean isRequired, Set<CheckBox> checkBoxes, String html) {
        super(fieldType, name, isRequired, html);
        this.checkBoxes = checkBoxes;
    }

    @Override
    public void edit(ProjectField projectField) {
        this.checkBoxes = ((CheckBoxContainer) projectField).getCheckBoxes();
        this.setName(projectField.getName());
        this.setIsRequired(projectField.getIsRequired());
    }
}
