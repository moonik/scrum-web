package scrumweb.dto.fieldcontent;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import scrumweb.dto.projectfield.CheckBoxDto;
import scrumweb.dto.projectfield.ProjectFieldDto;

import java.util.Set;

@NoArgsConstructor
@Getter @Setter
public class CheckBoxContainerContentDto extends FieldContentDto {

    private Set<CheckBoxDto> checkBoxes;

    public CheckBoxContainerContentDto(Long projectFieldId, String projectFieldName, Set<CheckBoxDto> checkBoxes) {
        super(projectFieldId, projectFieldName);
        this.checkBoxes = checkBoxes;
    }
}
