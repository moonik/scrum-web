package scrumweb.common.asm.fieldcontent;

import org.springframework.stereotype.Component;
import scrumweb.dto.fieldcontent.InputFieldContentDto;
import scrumweb.issue.fieldcontent.InputFieldContent;
import scrumweb.projectfield.domain.InputField;

@Component
public class InputFieldContentAsm implements FieldContentAsm<InputFieldContent, InputFieldContentDto, InputField> {

    @Override
    public InputFieldContent convertToEntityObject(InputField projectField, InputFieldContentDto fieldContentDto) {
        return new InputFieldContent(projectField, fieldContentDto.getContent());
    }

    @Override
    public InputFieldContentDto convertToDtoObject(InputFieldContent fieldContent) {
        return new InputFieldContentDto(fieldContent.getProjectField().getId(), fieldContent.getContent());
    }
}
