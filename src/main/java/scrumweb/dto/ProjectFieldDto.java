package scrumweb.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProjectFieldDto {
    private String fieldType;
    private String fieldName;
    private Boolean isRequired;
    private CheckBoxContainerDto checkBoxContainer;
    private RadioButtonContainerDto radioButtonContainerDto;
    private TextField textField;
}
