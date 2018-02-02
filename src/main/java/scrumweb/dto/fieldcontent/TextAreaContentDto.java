package scrumweb.dto.fieldcontent;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import scrumweb.dto.projectfield.ProjectFieldDto;

@NoArgsConstructor
@Getter
@Setter
public class TextAreaContentDto extends FieldContentDto {

    private String content;

    public TextAreaContentDto(Long projectFieldId, String content) {
        super(projectFieldId);
        this.content = content;
    }
}
