package scrumweb.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CheckBoxDto {
    private Long id;
    private Boolean isSelected;
    private String value;

    public CheckBoxDto(Long id, Boolean isSelected, String value) {
        this.id = id;
        this.isSelected = isSelected;
        this.value = value;
    }
}
