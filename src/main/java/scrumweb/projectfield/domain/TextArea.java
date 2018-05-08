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

    private int maxCharacters;

    public TextArea(FieldType fieldType, String name, Boolean isRequired, int maxCharacters) {
        super(fieldType, name, isRequired);
        this.maxCharacters = maxCharacters;
    }

    @Override
    public void edit(ProjectField projectField) {
        this.maxCharacters = ((TextArea) projectField).getMaxCharacters();
        this.setName(projectField.getName());
        this.setIsRequired(projectField.getIsRequired());
    }
}
