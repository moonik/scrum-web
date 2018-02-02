package scrumweb.common.asm.fieldcontent;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import scrumweb.dto.fieldcontent.CheckBoxContainerContentDto;
import scrumweb.issue.field.CheckBoxContent;
import scrumweb.projectfield.domain.CheckBoxContainer;

@Component
@AllArgsConstructor
public class CheckBoxContainerContentAsm implements FieldContentAsm<CheckBoxContent, CheckBoxContainerContentDto, CheckBoxContainer> {

    @Override
    public CheckBoxContent convertToEntityObject(CheckBoxContainer projectField, CheckBoxContainerContentDto fieldContentDto) {
        return null;
    }

    @Override
    public CheckBoxContainerContentDto convertToDtoObject(CheckBoxContent fieldContent) {
        return null;
    }
}
