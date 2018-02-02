package scrumweb.dto.fieldcontent;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import scrumweb.dto.projectfield.ProjectFieldDto;

import java.util.Set;

@NoArgsConstructor
@Getter @Setter
public class RadioButtonContainerContentDto extends FieldContentDto {
    private RadioButtonDto radioButton;

    public RadioButtonContainerContentDto(Long projectFieldId, RadioButtonDto radioButton) {
        super(projectFieldId);
        this.radioButton = radioButton;
    }
}
