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
    private int maxCharacters;
    private int minCharacters;

    public InputFieldDto(Long id, String fieldType, String fieldName, Boolean isRequired, int maxCharacters, int minCharacters) {
        super(id, fieldType, fieldName, isRequired);
        this.maxCharacters = maxCharacters;
        this.minCharacters = minCharacters;
    }
}
