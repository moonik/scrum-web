package scrumweb.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProjectFieldDto {
    private String fieldType;
    private String fieldName;
    private Boolean isRequired;
    private CheckBoxContainerDto checkBoxContainer;
    private RadioButtonContainerDto radioButtonContainerDto;
    private TextField textField;
    private String projectName;
    private String issueType;

    public ProjectFieldDto(String fieldType, String fieldName, Boolean isRequired, CheckBoxContainerDto checkBoxContainer, RadioButtonContainerDto radioButtonContainerDto, TextField textField) {
        this.fieldType = fieldType;
        this.fieldName = fieldName;
        this.isRequired = isRequired;
        this.checkBoxContainer = checkBoxContainer;
        this.radioButtonContainerDto = radioButtonContainerDto;
        this.textField = textField;
    }
}
