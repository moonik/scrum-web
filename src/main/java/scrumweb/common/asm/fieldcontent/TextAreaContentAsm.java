package scrumweb.common.asm.fieldcontent;

import org.springframework.stereotype.Component;
import scrumweb.dto.fieldcontent.TextAreaContentDto;
import scrumweb.issue.fieldcontent.TextAreaContent;
import scrumweb.projectfield.domain.TextArea;

@Component
public class TextAreaContentAsm implements FieldContentAsm<TextAreaContent, TextAreaContentDto, TextArea> {

    @Override
    public TextAreaContent createEntityObject(TextArea projectField, TextAreaContentDto fieldContentDto) {
        return new TextAreaContent(projectField, fieldContentDto.getContent());
    }

    @Override
    public TextAreaContentDto createDtoObject(TextAreaContent fieldContent) {
        return new TextAreaContentDto(
                fieldContent.getProjectField().getId(),
                fieldContent.getProjectField().getName(),
                fieldContent.getContent()
        );
    }
}
