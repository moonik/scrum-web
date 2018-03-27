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
@Getter @Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class RadioButtonContainer extends ProjectField {

    @OneToMany(cascade = CascadeType.ALL)
    private Set<RadioButton> radioButtons;

    public RadioButtonContainer(FieldType fieldType, String name, Boolean isRequired, Set<RadioButton> radioButtons, String html) {
        super(fieldType, name, isRequired, html);
        this.radioButtons = radioButtons;
    }

    @Override
    public void edit(ProjectField projectField) {
        this.radioButtons = ((RadioButtonContainer) projectField).getRadioButtons();
        this.setName(projectField.getName());
        this.setIsRequired(projectField.getIsRequired());
    }
}
