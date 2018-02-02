package scrumweb.dto.fieldcontent;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import scrumweb.dto.projectfield.ProjectFieldDto;

import java.util.Set;

@NoArgsConstructor
@Getter @Setter
public class CheckBoxContainerContentDto extends FieldContentDto {

    private Set<CheckBoxDto> checkBoxes;

    public CheckBoxContainerContentDto(Long id, Set<CheckBoxDto> checkBoxes) {
        super(id);
        this.checkBoxes = checkBoxes;
    }
}
