package scrumweb.dto.projectfield;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class ProjectFieldDto {
    private Long id;
    private String fieldType;
    private String fieldName;
    private Boolean isRequired;
    private Boolean submitted = true;

    public ProjectFieldDto(Long id, String fieldType, String fieldName, Boolean isRequired) {
        this.id = id;
        this.fieldType = fieldType;
        this.fieldName = fieldName;
        this.isRequired = isRequired;
    }
}
