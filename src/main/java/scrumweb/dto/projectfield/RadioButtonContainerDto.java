package scrumweb.dto.projectfield;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@Getter @Setter
public class RadioButtonContainerDto extends ProjectFieldDto {
    private Set<RadioButtonDto> radioButtons;

    public RadioButtonContainerDto(Long id, String fieldType, String fieldName, Boolean isRequired, Set<RadioButtonDto> radioButtons) {
        super(id, fieldType, fieldName, isRequired);
        this.radioButtons = radioButtons;
    }
}
