package scrumweb.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InputFieldDto {
    private Long id;
    private String content;
    private int maxCharacters;
    private int minCharacters;

    public InputFieldDto(Long id, String content, int maxCharacters, int minCharacters) {
        this.id = id;
        this.content = content;
        this.maxCharacters = maxCharacters;
        this.minCharacters = minCharacters;
    }
}
