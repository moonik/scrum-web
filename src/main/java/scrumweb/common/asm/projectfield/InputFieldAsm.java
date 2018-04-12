package scrumweb.common.asm.projectfield;

import org.springframework.stereotype.Component;
import scrumweb.dto.projectfield.InputFieldDto;
import scrumweb.dto.projectfield.ProjectFieldDto;
import scrumweb.projectfield.domain.InputField;
import scrumweb.projectfield.domain.ProjectField.FieldType;

@Component
public class InputFieldAsm implements ProjectFieldAsm<InputField, InputFieldDto> {

    @Override
    public InputField createEntityObject(InputFieldDto projectFieldDto) {
        return new InputField(
                FieldType.getFieldType(projectFieldDto.getFieldType()),
                projectFieldDto.getFieldName(),
                projectFieldDto.getIsRequired(),
                projectFieldDto.getMinCharacters(),
                getMaxChars(projectFieldDto)
        );
    }

    @Override
    public InputFieldDto createDtoObject(InputField projectField) {
        return new InputFieldDto(
                projectField.getId(),
                projectField.getFieldType().name(),
                projectField.getName(),
                projectField.getIsRequired(),
                projectField.getMaxCharacters(),
                projectField.getMinCharacters()
        );
    }

    private int getMaxChars(InputFieldDto projectFieldDto) {
        return projectFieldDto.getMaxCharacters() == 0 ?
                255 : projectFieldDto.getMaxCharacters();
    }
}