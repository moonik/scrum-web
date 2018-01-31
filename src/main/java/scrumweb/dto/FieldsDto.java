package scrumweb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class FieldsDto {

    private Set<InputFieldDto> inputFieldDtos;
    private Set<CheckBoxContainerDto> checkBoxContainerDtos;
    private Set<ListElementsContainerDto> listElementsContainerDtos;
    private Set<RadioButtonContainerDto> radioButtonContainerDtos;
    private Set<TextAreaDto> textAreaDtos;
}
