package scrumweb.project.field;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NoArgsConstructor
@Getter @Setter
public class InputField extends ProjectField{

    private int minCharacters;
    private int maxCharacters;

    public InputField(FieldType fieldType, String name, Boolean isRequired, int minCharacters, int maxCharacters) {
        super(fieldType, name, isRequired);
        this.minCharacters = minCharacters;
        this.maxCharacters = maxCharacters;
    }
}