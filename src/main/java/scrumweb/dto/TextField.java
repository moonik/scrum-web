package scrumweb.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TextField {
    private Long id;
    private Boolean isTextArea;
    private String content;
    private int maxCharacters;
    private int minCharacters;

    public TextField(Long id, Boolean isTextArea, String content, int maxCharacters, int minCharacters) {
        this.id = id;
        this.isTextArea = isTextArea;
        this.content = content;
        this.maxCharacters = maxCharacters;
        this.minCharacters = minCharacters;
    }
}
