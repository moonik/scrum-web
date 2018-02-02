package scrumweb.common.asm.projectfield;

import org.springframework.stereotype.Component;
import scrumweb.dto.projectfield.TextAreaDto;
import scrumweb.projectfield.domain.ProjectField;
import scrumweb.projectfield.domain.TextArea;

@Component
public class TextAreaAsm implements ProjectFieldAsm<TextArea, TextAreaDto> {

    @Override
    public TextArea convertToEntityObject(TextAreaDto projectFieldDto) {
        return new TextArea(
                ProjectField.FieldType.getFieldType(projectFieldDto.getFieldType()),
                projectFieldDto.getFieldName(),
                projectFieldDto.getIsRequired(),
                projectFieldDto.getMinCharacters(),
                projectFieldDto.getMaxCharacters()
        );
    }

    @Override
    public TextAreaDto convertToDtoObject(TextArea projectField) {
        return new TextAreaDto(
                projectField.getId(),
                projectField.getFieldType().toString(),
                projectField.getName(),
                projectField.getIsRequired(),
                projectField.getMaxCharacters(),
                projectField.getMinCharacters());
    }
}
