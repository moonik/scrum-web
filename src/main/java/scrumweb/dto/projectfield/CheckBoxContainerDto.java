package scrumweb.dto.projectfield;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@Getter @Setter
public class CheckBoxContainerDto extends ProjectFieldDto {
    private Set<CheckBoxDto> elements;

    public CheckBoxContainerDto(Long id, String fieldType, String fieldName, Boolean isRequired, Set<CheckBoxDto> elements) {
        super(id, fieldType, fieldName, isRequired);
        this.elements = elements;
    }
}
