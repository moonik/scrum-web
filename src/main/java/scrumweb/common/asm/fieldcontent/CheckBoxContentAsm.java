package scrumweb.common.asm.fieldcontent;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import scrumweb.common.asm.common.FieldElementsAsm;
import scrumweb.dto.fieldcontent.CheckBoxContainerContentDto;
import scrumweb.dto.projectfield.CheckBoxDto;
import scrumweb.issue.fieldcontent.CheckBoxContent;
import scrumweb.projectfield.domain.CheckBox;
import scrumweb.projectfield.domain.CheckBoxContainer;
import scrumweb.projectfield.repository.CheckBoxRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CheckBoxContentAsm implements FieldContentAsm<CheckBoxContent, CheckBoxContainerContentDto, CheckBoxContainer> {

    private FieldElementsAsm<CheckBox, CheckBoxDto> fieldElementsAsm;
    private CheckBoxRepository checkBoxRepository;

    @Override
    public CheckBoxContent createEntityObject(CheckBoxContainer projectField, CheckBoxContainerContentDto fieldContentDto) {
        return new CheckBoxContent(projectField, checkBoxRepository.getCheckBoxes(extractIds(fieldContentDto.getCheckBoxes())));
    }

    @Override
    public CheckBoxContainerContentDto createDtoObject(CheckBoxContent fieldContent) {
        Set<CheckBoxDto> checkBoxes = fieldContent.getCheckBoxes().stream().map(checkBox -> fieldElementsAsm.convertToDtoObject(checkBox)).collect(Collectors.toSet());
        return new CheckBoxContainerContentDto(
                fieldContent.getProjectField().getId(),
                fieldContent.getProjectField().getName(),
                checkBoxes
        );
    }

    private Set<Long> extractIds(Set<CheckBoxDto> checkBoxes) {
        return checkBoxes.stream()
                .map(CheckBoxDto::getId)
                .collect(Collectors.toSet());
    }
}
