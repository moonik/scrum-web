package scrumweb.common.asm.fieldcontent;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import scrumweb.common.asm.common.FieldElementsAsm;
import scrumweb.dto.fieldcontent.RadioButtonContainerContentDto;
import scrumweb.dto.projectfield.RadioButtonDto;
import scrumweb.issue.fieldcontent.RadioButtonContent;
import scrumweb.projectfield.domain.RadioButton;
import scrumweb.projectfield.domain.RadioButtonContainer;
import scrumweb.projectfield.repository.FieldElementsRepository;

@Component
@AllArgsConstructor
public class RadioButtonContentAsm implements FieldContentAsm<RadioButtonContent, RadioButtonContainerContentDto, RadioButtonContainer> {

    private FieldElementsAsm<RadioButton, RadioButtonDto> fieldElementsAsm;
    private FieldElementsRepository<RadioButton> radioButtonRepository;

    @Override
    public RadioButtonContent createEntityObject(RadioButtonContainer projectField, RadioButtonContainerContentDto fieldContentDto) {
        return new RadioButtonContent(projectField, radioButtonRepository.getOne(fieldContentDto.getRadioButton().getId()));
    }

    @Override
    public RadioButtonContainerContentDto createDtoObject(RadioButtonContent fieldContent) {
        return new RadioButtonContainerContentDto(fieldContent.getProjectField().getId(), fieldElementsAsm.convertToDtoObject(fieldContent.getRadioButton()));
    }
}