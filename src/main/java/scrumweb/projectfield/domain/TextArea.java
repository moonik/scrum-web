package scrumweb.projectfield.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Getter @Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class TextArea extends ProjectField {

    private int minCharacters;
    private int maxCharacters;

    public TextArea(FieldType fieldType, String name, Boolean isRequired, int minCharacters, int maxCharacters) {
        super(fieldType, name, isRequired);
        this.minCharacters = minCharacters;
        this.maxCharacters = maxCharacters;
    }
}
