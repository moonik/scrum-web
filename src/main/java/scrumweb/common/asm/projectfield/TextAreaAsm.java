package scrumweb.common.asm.projectfield;

import org.springframework.stereotype.Component;
import scrumweb.dto.projectfield.TextAreaDto;
import scrumweb.projectfield.domain.ProjectField.FieldType;
import scrumweb.projectfield.domain.TextArea;

@Component
public class TextAreaAsm implements ProjectFieldAsm<TextArea, TextAreaDto> {
    //TODO ADD HTML
    @Override
    public TextArea createEntityObject(TextAreaDto projectFieldDto) {
        return new TextArea(
                FieldType.getFieldType(projectFieldDto.getFieldType()),
                projectFieldDto.getFieldName(),
                projectFieldDto.getIsRequired(),
                projectFieldDto.getMinCharacters(),
                projectFieldDto.getMaxCharacters(),
                createHtml(projectFieldDto)
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

    @Override
    public String createHtml(TextAreaDto projectFieldDto) {
        return null;
    }
}
