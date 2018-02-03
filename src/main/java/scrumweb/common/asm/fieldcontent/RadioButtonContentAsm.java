package scrumweb.common.asm.fieldcontent;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import scrumweb.common.asm.common.FieldElementsAsm;
import scrumweb.dto.fieldcontent.RadioButtonContainerContentDto;
import scrumweb.dto.projectfield.RadioButtonDto;
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
    public RadioButtonContent createEntityObject(RadioButtonContainer projectField, RadioButtonContainerContentDto fieldContentDto) {
        return new RadioButtonContent(projectField, radioButtonRepository.findOne(fieldContentDto.getRadioButton().getId()));
    }

    @Override
    public RadioButtonContainerContentDto createDtoObject(RadioButtonContent fieldContent) {
        return new RadioButtonContainerContentDto(
                fieldContent.getProjectField().getId(),
                fieldContent.getProjectField().getName(),
                fieldElementsAsm.convertToDtoObject(fieldContent.getRadioButton())
        );
    }
}