package scrumweb.dto.projectfield;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@Getter @Setter
public class ListElementsContainerDto extends ProjectFieldDto {
    private Set<ListElementDto> elements;

    public ListElementsContainerDto(Long id, String fieldType, String fieldName, Boolean isRequired, Set<ListElementDto> elements) {
        super(id, fieldType, fieldName, isRequired);
        this.elements = elements;
    }
}
