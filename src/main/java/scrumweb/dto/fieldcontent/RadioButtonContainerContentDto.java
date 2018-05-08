package scrumweb.dto.fieldcontent;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import scrumweb.dto.projectfield.RadioButtonDto;

@NoArgsConstructor
@Getter @Setter
public class RadioButtonContainerContentDto extends FieldContentDto {
    private RadioButtonDto element;

    public RadioButtonContainerContentDto(Long projectFieldId, String projectFieldName, RadioButtonDto element) {
        super(projectFieldId, projectFieldName);
        this.element = element;
    }
}
