package scrumweb.common.asm.fieldcontent;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import scrumweb.common.asm.common.FieldElementsAsm;
import scrumweb.dto.fieldcontent.RadioButtonContainerContentDto;
import scrumweb.dto.fieldcontent.RadioButtonDto;
import scrumweb.issue.fieldcontent.RadioButtonContent;
import scrumweb.projectfield.domain.RadioButton;
import scrumweb.projectfield.domain.RadioButtonContainer;
import scrumweb.projectfield.repository.RadioButtonRepository;

@Component
@AllArgsConstructor
public class RadioButtonContentAsm implements FieldContentAsm<RadioButtonContent, RadioButtonContainerContentDto, RadioButtonContainer> {

    private FieldElementsAsm<RadioButton, RadioButtonDto> fieldElementsAsm;
    private RadioButtonRepository radioButtonRepository;

    @Override
    public RadioButtonContent convertToEntityObject(RadioButtonContainer projectField, RadioButtonContainerContentDto fieldContentDto) {
        return new RadioButtonContent(projectField, radioButtonRepository.getOne(fieldContentDto.getRadioButton().getId()));
    }

    @Override
    public RadioButtonContainerContentDto convertToDtoObject(RadioButtonContent fieldContent) {
        return new RadioButtonContainerContentDto(fieldContent.getProjectField().getId(), fieldElementsAsm.convertToDtoObject(fieldContent.getRadioButton()));
    }
}