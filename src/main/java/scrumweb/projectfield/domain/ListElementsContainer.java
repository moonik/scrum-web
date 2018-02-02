package scrumweb.projectfield.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
@Getter @Setter
@NoArgsConstructor
public class ListElementsContainer extends ProjectField {

    @OneToMany
    private Set<ListElement> elements;

    public ListElementsContainer(FieldType fieldType, String name, Boolean isRequired, Set<ListElement> elements) {
        super(fieldType, name, isRequired);
        this.elements = elements;
    }
}
