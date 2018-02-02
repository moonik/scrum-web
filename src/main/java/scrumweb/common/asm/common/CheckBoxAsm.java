package scrumweb.common.asm.common;

import org.springframework.stereotype.Component;
import scrumweb.dto.projectfield.CheckBoxDto;
import scrumweb.projectfield.domain.CheckBox;

@Component
public class CheckBoxAsm implements FieldElementsAsm<CheckBox, CheckBoxDto> {
    @Override
    public CheckBox convertToEntityObject(CheckBoxDto element) {
        return new CheckBox(element.getValue());
    }

    @Override
    public CheckBoxDto convertToDtoObject(CheckBox element) {
        return new CheckBoxDto(element.getId(), element.getValue());
    }
}
