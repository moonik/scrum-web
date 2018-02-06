package scrumweb.dto.projectfield;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TextAreaDto extends ProjectFieldDto {
    private int maxCharacters;
    private int minCharacters;

    public TextAreaDto(Long id, String fieldType, String fieldName, Boolean isRequired, int maxCharacters, int minCharacters) {
        super(id, fieldType, fieldName, isRequired);
        this.maxCharacters = maxCharacters;
        this.minCharacters = minCharacters;
    }
}
