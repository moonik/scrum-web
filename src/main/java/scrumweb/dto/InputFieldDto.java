package scrumweb.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper=false)
public class InputFieldDto extends ProjectFieldDto {
    private String content;
    private int maxCharacters;
    private int minCharacters;

    public InputFieldDto(Long id, String fieldType, String fieldName, Boolean isRequired, String content, int maxCharacters, int minCharacters) {
        this.content = content;
        this.maxCharacters = maxCharacters;
        this.minCharacters = minCharacters;
        setId(id);
        setFieldType(fieldType);
        setFieldName(fieldName);
        setIsRequired(isRequired);
    }
}
