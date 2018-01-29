package scrumweb.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@Getter @Setter
public class RadioButtonContainerDto {
    private Long id;
    private Set<RadioButtonDto> radioButtonDtos;

    public RadioButtonContainerDto(Set<RadioButtonDto> radioButtonDtos) {
        this.radioButtonDtos = radioButtonDtos;
    }
}
