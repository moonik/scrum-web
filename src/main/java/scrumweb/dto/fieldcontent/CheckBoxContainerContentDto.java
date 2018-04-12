package scrumweb.dto.fieldcontent;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import scrumweb.dto.projectfield.CheckBoxDto;

import java.util.Set;

@NoArgsConstructor
@Getter @Setter
public class CheckBoxContainerContentDto extends FieldContentDto {

    private Set<CheckBoxDto> elements;

    public CheckBoxContainerContentDto(Long projectFieldId, String projectFieldName, Set<CheckBoxDto> elements) {
        super(projectFieldId, projectFieldName);
        this.elements = elements;
    }
}
