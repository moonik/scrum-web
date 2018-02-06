package scrumweb.common.asm.projectfield;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import scrumweb.common.asm.common.FieldElementsAsm;
import scrumweb.dto.projectfield.RadioButtonContainerDto;
import scrumweb.dto.projectfield.RadioButtonDto;
import scrumweb.projectfield.domain.ProjectField.FieldType;
import scrumweb.projectfield.domain.RadioButton;
import scrumweb.projectfield.domain.RadioButtonContainer;
import scrumweb.projectfield.repository.RadioButtonRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class RadioButtonContainerAsm implements ProjectFieldAsm<RadioButtonContainer, RadioButtonContainerDto> {

    private FieldElementsAsm<RadioButton, RadioButtonDto> fieldElementsAsm;

    @Override
    public RadioButtonContainer createEntityObject(RadioButtonContainerDto projectFieldDto) {
        Set<RadioButton> radioButtons = projectFieldDto.getRadioButtons().stream().map(radioButtonDto -> fieldElementsAsm.convertToEntityObject(radioButtonDto)).collect(Collectors.toSet());
        return new RadioButtonContainer(FieldType.getFieldType(projectFieldDto.getFieldType()), projectFieldDto.getFieldName(), projectFieldDto.getIsRequired(), radioButtons);
    }

    @Override
    public RadioButtonContainerDto createDtoObject(RadioButtonContainer projectField) {
        Set<RadioButtonDto> radioButtons = projectField.getRadioButtons().stream().map(radioButton -> fieldElementsAsm.convertToDtoObject(radioButton)).collect(Collectors.toSet());
        return new RadioButtonContainerDto(
                projectField.getId(),
                projectField.getFieldType().toString(),
                projectField.getName(),
                projectField.getIsRequired(),
                radioButtons
        );
    }
}
