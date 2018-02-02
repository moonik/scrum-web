package scrumweb.common.asm.projectfield;

import org.springframework.stereotype.Component;
import scrumweb.dto.projectfield.InputFieldDto;
import scrumweb.projectfield.domain.InputField;
import scrumweb.projectfield.domain.ProjectField;

@Component
public class InputFieldAsm implements ProjectFieldAsm<InputField, InputFieldDto> {

    @Override
    public InputField convertToEntityObject(InputFieldDto projectFieldDto) {
        return new InputField(
                ProjectField.FieldType.getFieldType(projectFieldDto.getFieldType()),
                projectFieldDto.getFieldName(),
                projectFieldDto.getIsRequired(),
                projectFieldDto.getMinCharacters(),
                projectFieldDto.getMaxCharacters()
        );
    }

    @Override
    public InputFieldDto convertToDtoObject(InputField projectField) {
        return new InputFieldDto(
                projectField.getId(),
                projectField.getFieldType().toString(),
                projectField.getName(),
                projectField.getIsRequired(),
                projectField.getMaxCharacters(),
                projectField.getMinCharacters()
        );
    }
}