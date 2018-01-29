package scrumweb.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@Getter @Setter
public class CheckBoxContainerDto {
    private Long id;
    private Set<CheckBoxDto> checkBoxes;

    public CheckBoxContainerDto(Long id, Set<CheckBoxDto> checkBoxes) {
        this.id = id;
        this.checkBoxes = checkBoxes;
    }
}
