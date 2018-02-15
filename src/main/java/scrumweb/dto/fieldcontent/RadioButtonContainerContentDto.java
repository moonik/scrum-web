package scrumweb.dto.fieldcontent;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import scrumweb.dto.projectfield.RadioButtonDto;

@NoArgsConstructor
@Getter @Setter
public class RadioButtonContainerContentDto extends FieldContentDto {
    private RadioButtonDto radioButton;

    public RadioButtonContainerContentDto(Long projectFieldId, String projectFieldName, RadioButtonDto radioButton) {
        super(projectFieldId, projectFieldName);
        this.radioButton = radioButton;
    }
}
