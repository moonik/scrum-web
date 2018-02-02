package scrumweb.common.asm.projectfield;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import scrumweb.dto.projectfield.RadioButtonContainerDto;
import scrumweb.dto.projectfield.RadioButtonDto;
import scrumweb.projectfield.domain.ProjectField;
import scrumweb.projectfield.domain.RadioButton;
import scrumweb.projectfield.domain.RadioButtonContainer;
import scrumweb.projectfield.repository.RadioButtonRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class RadioButtonContainerAsm implements ProjectFieldAsm<RadioButtonContainer, RadioButtonContainerDto> {

    private RadioButtonRepository radioButtonRepository;

    @Override
    public RadioButtonContainer convertToEntityObject(RadioButtonContainerDto projectFieldDto) {
        Set<RadioButton> radioButtons = projectFieldDto.getRadioButtons().stream().map(this::createRadioButton).collect(Collectors.toSet());
        radioButtonRepository.save(radioButtons);
        return new RadioButtonContainer(
                ProjectField.FieldType.getFieldType(projectFieldDto.getFieldType()),
                projectFieldDto.getFieldName(),
                projectFieldDto.getIsRequired(),
                radioButtons
        );
    }

    @Override
    public RadioButtonContainerDto convertToDtoObject(RadioButtonContainer projectField) {
        return new RadioButtonContainerDto(
                projectField.getId(),
                projectField.getFieldType().toString(),
                projectField.getName(),
                projectField.getIsRequired(),
                projectField.getRadioButtons().stream()
                        .map(this::createRadioButtonDto)
                        .collect(Collectors.toSet())
        );
    }

    private RadioButtonDto createRadioButtonDto(RadioButton radioButton) {
        return new RadioButtonDto(radioButton.getId(), radioButton.getValue());
    }

    private RadioButton createRadioButton(RadioButtonDto radioButtonDto) {
        return new RadioButton(radioButtonDto.getValue());
    }
}
