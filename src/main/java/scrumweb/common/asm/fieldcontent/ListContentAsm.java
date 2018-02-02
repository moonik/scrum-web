package scrumweb.common.asm.fieldcontent;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import scrumweb.common.asm.common.FieldElementsAsm;
import scrumweb.dto.fieldcontent.ListElementDto;
import scrumweb.dto.fieldcontent.ListElementsContainerContentDto;
import scrumweb.issue.fieldcontent.ListContent;
import scrumweb.projectfield.domain.ListElement;
import scrumweb.projectfield.domain.ListElementsContainer;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ListContentAsm implements FieldContentAsm<ListContent, ListElementsContainerContentDto, ListElementsContainer> {

    private FieldElementsAsm<ListElement, ListElementDto> fieldElementsAsm;

    @Override
    public ListContent convertToEntityObject(ListElementsContainer projectField, ListElementsContainerContentDto fieldContentDto) {
        Set<ListElement> listElements = fieldContentDto.getListElement().stream().map(listElementDto -> fieldElementsAsm.convertToEntityObject(listElementDto)).collect(Collectors.toSet());
        return new ListContent(projectField, listElements);
    }

    @Override
    public ListElementsContainerContentDto convertToDtoObject(ListContent fieldContent) {
        return null;
    }
}
