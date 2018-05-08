package scrumweb.projectfield.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NoArgsConstructor
@Getter @Setter
public class InputField extends ProjectField{

    private int maxCharacters;

    public InputField(FieldType fieldType, String name, Boolean isRequired, int maxCharacters) {
        super(fieldType, name, isRequired);
        this.maxCharacters = maxCharacters;
    }

    @Override
    public void edit(ProjectField projectField) {
        this.maxCharacters = ((InputField) projectField).getMaxCharacters();
        this.setName(projectField.getName());
        this.setIsRequired(projectField.getIsRequired());
    }
}