package scrumweb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@Getter @Setter
public class CheckBoxContainerDto extends ProjectFieldDto {
    private Set<CheckBoxDto> checkBoxes;

    public CheckBoxContainerDto(Long id, String fieldType, String fieldName, Boolean isRequired, Set<CheckBoxDto> checkBoxes) {
        super(id, fieldType, fieldName, isRequired);
        this.checkBoxes = checkBoxes;
    }
}
