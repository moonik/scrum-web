package scrumweb.dto.fieldcontent;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import scrumweb.dto.projectfield.ListElementDto;

import java.util.Set;

@NoArgsConstructor
@Getter @Setter
public class ListElementsContainerContentDto extends FieldContentDto {
    private Set<ListElementDto> listElements;

    public ListElementsContainerContentDto(Long projectFieldId, String projectFieldName, Set<ListElementDto> listElements) {
        super(projectFieldId, projectFieldName);
        this.listElements = listElements;
    }
}
