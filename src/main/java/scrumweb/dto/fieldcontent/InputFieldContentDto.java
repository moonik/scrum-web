package scrumweb.dto.fieldcontent;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InputFieldContentDto extends FieldContentDto {

    private String content;

    public InputFieldContentDto(Long projectFieldId, String projectFieldName, String content) {
        super(projectFieldId, projectFieldName);
        this.content = content;
    }
}
