package scrumweb.common.asm.projectfield;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import scrumweb.common.asm.common.FieldElementsAsm;
import scrumweb.dto.projectfield.CheckBoxContainerDto;
import scrumweb.dto.projectfield.CheckBoxDto;
import scrumweb.projectfield.domain.CheckBox;
import scrumweb.projectfield.domain.CheckBoxContainer;
import scrumweb.projectfield.domain.ProjectField.FieldType;
import scrumweb.projectfield.repository.CheckBoxRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CheckBoxContainerAsm implements ProjectFieldAsm<CheckBoxContainer, CheckBoxContainerDto> {

    private CheckBoxRepository checkBoxRepository;
    private FieldElementsAsm<CheckBox, CheckBoxDto> fieldElementsAsm;

    @Override
    public CheckBoxContainer convertToEntityObject(CheckBoxContainerDto projectFieldDto) {
        Set<CheckBox> checkBoxes = projectFieldDto.getCheckBoxes().stream().map(checkBoxDto -> fieldElementsAsm.convertToEntityObject(checkBoxDto)).collect(Collectors.toSet());
        checkBoxRepository.save(checkBoxes);
        return new CheckBoxContainer(FieldType.getFieldType(projectFieldDto.getFieldType()), projectFieldDto.getFieldName(), projectFieldDto.getIsRequired(), checkBoxes);
    }

    @Override
    public CheckBoxContainerDto convertToDtoObject(CheckBoxContainer projectField) {
        Set<CheckBoxDto> checkBoxes = projectField.getCheckBoxes().stream().map(checkBox -> fieldElementsAsm.convertToDtoObject(checkBox)).collect(Collectors.toSet());
        return new CheckBoxContainerDto(
                projectField.getId(),
                projectField.getFieldType().toString(),
                projectField.getName(),
                projectField.getIsRequired(),
                checkBoxes
        );
    }
}
