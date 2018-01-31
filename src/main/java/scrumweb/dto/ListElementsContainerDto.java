package scrumweb.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@Getter @Setter
public class ListElementsContainerDto extends ProjectFieldDto {
    private Set<ListElementDto> listElements;

    public ListElementsContainerDto(Long id, String fieldType, String fieldName, Boolean isRequired, Set<ListElementDto> listElements) {
        super(id, fieldType, fieldName, isRequired);
        this.listElements = listElements;
    }
}
