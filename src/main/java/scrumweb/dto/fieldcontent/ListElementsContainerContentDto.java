package scrumweb.dto.fieldcontent;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import scrumweb.dto.projectfield.ProjectFieldDto;

import java.util.Set;

@NoArgsConstructor
@Getter @Setter
public class ListElementsContainerContentDto extends FieldContentDto {
    private ListElementDto listElement;

    public ListElementsContainerContentDto(Long projectFieldId, ListElementDto listElement) {
        super(projectFieldId);
        this.listElement = listElement;
    }
}
