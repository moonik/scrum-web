package scrumweb.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TextAreaDto extends ProjectFieldDto {
    private String content;
    private int maxCharacters;
    private int minCharacters;

    public TextAreaDto(Long id, String fieldType, String fieldName, Boolean isRequired, String content, int maxCharacters, int minCharacters) {
        super(id, fieldType, fieldName, isRequired);
        this.content = content;
        this.maxCharacters = maxCharacters;
        this.minCharacters = minCharacters;
    }
}
