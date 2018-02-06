package scrumweb.common.asm.common;

import org.springframework.stereotype.Component;
import scrumweb.dto.projectfield.RadioButtonDto;
import scrumweb.projectfield.domain.RadioButton;

@Component
public class RadioButtonAsm implements FieldElementsAsm<RadioButton, RadioButtonDto> {
    @Override
    public RadioButton convertToEntityObject(RadioButtonDto element) {
        return new RadioButton(element.getValue());
    }

    @Override
    public RadioButtonDto convertToDtoObject(RadioButton element) {
        return new RadioButtonDto(element.getId(), element.getValue());
    }
}
