package scrumweb.projectfield.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
@Getter @Setter
@NoArgsConstructor
public class ListElementsContainer extends ProjectField {

    @OneToMany(cascade = CascadeType.ALL)
    private Set<ListElement> elements;

    public ListElementsContainer(FieldType fieldType, String name, Boolean isRequired, Set<ListElement> elements, String html) {
        super(fieldType, name, isRequired, html);
        this.elements = elements;
    }

    @Override
    public void edit(ProjectField projectField) {
        this.elements = ((ListElementsContainer) projectField).getElements();
        this.setName(projectField.getName());
        this.setIsRequired(projectField.getIsRequired());
    }
}
