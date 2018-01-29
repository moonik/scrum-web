package scrumweb.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter @Setter
public class RadioButtonDto {
    private Long id;
    private String value;
    private Boolean isSelected;
}
