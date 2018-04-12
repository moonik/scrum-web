package scrumweb.common.asm.projectfield;

import org.springframework.stereotype.Component;
import scrumweb.dto.projectfield.InputFieldDto;
import scrumweb.dto.projectfield.TextAreaDto;
import scrumweb.projectfield.domain.ProjectField.FieldType;
import scrumweb.projectfield.domain.TextArea;

@Component
public class TextAreaAsm implements ProjectFieldAsm<TextArea, TextAreaDto> {

    @Override
    public TextArea createEntityObject(TextAreaDto projectFieldDto) {
        return new TextArea(
                FieldType.getFieldType(projectFieldDto.getFieldType()),
                projectFieldDto.getFieldName(),
                projectFieldDto.getIsRequired(),
                projectFieldDto.getMinCharacters(),
                getMaxChars(projectFieldDto)
        );
    }

    @Override
    public TextAreaDto createDtoObject(TextArea projectField) {
        return new TextAreaDto(
                projectField.getId(),
                projectField.getFieldType().toString(),
                projectField.getName(),
                projectField.getIsRequired(),
                projectField.getMaxCharacters(),
                projectField.getMinCharacters());
    }

    private int getMaxChars(TextAreaDto projectFieldDto) {
        return projectFieldDto.getMaxCharacters() == 0 ?
                255 : projectFieldDto.getMaxCharacters();
    }
}
