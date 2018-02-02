package scrumweb.dto.projectfield;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InputFieldDto extends ProjectFieldDto {
    private String content;
    private int maxCharacters;
    private int minCharacters;

    public InputFieldDto(Long id, String fieldType, String fieldName, Boolean isRequired, String content, int maxCharacters, int minCharacters) {
        super(id, fieldType, fieldName, isRequired);
        this.content = content;
        this.maxCharacters = maxCharacters;
        this.minCharacters = minCharacters;
    }
}
