package scrumweb.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class RadioButtonDto {
    private Long id;
    private String value;
    private Boolean isSelected;

    public RadioButtonDto(Long id, String value) {
        this.id = id;
        this.value = value;
    }
}
