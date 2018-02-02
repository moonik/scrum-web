package scrumweb.dto.fieldcontent;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import scrumweb.dto.projectfield.ProjectFieldDto;

import java.util.Set;

@NoArgsConstructor
@Getter @Setter
public class ListElementsContainerContentDto extends FieldContentDto {
    private Set<ListElementDto> listElements;

    public ListElementsContainerContentDto(Long projectFieldId, Set<ListElementDto> listElements) {
        super(projectFieldId);
        this.listElements = listElements;
    }
}
