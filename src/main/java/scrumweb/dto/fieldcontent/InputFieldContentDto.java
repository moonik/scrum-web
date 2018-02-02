package scrumweb.dto.fieldcontent;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import scrumweb.dto.projectfield.ProjectFieldDto;

@NoArgsConstructor
@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InputFieldContentDto extends FieldContentDto {

    private String content;

    public InputFieldContentDto(Long projectFieldId, String content) {
        super(projectFieldId);
        this.content = content;
    }
}
