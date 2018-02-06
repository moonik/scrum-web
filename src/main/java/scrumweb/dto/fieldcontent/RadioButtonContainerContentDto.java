package scrumweb.dto.fieldcontent;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import scrumweb.dto.projectfield.ProjectFieldDto;
import scrumweb.dto.projectfield.RadioButtonDto;

import java.util.Set;

@NoArgsConstructor
@Getter @Setter
public class RadioButtonContainerContentDto extends FieldContentDto {
    private RadioButtonDto radioButton;

    public RadioButtonContainerContentDto(Long projectFieldId, String projectFieldName, RadioButtonDto radioButton) {
        super(projectFieldId, projectFieldName);
        this.radioButton = radioButton;
    }
}
