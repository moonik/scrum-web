package scrumweb.common.asm.common;

import org.springframework.stereotype.Component;
import scrumweb.dto.projectfield.ListElementDto;
import scrumweb.projectfield.domain.ListElement;

@Component
public class ListElementAsm implements FieldElementsAsm<ListElement, ListElementDto> {
    @Override
    public ListElement convertToEntityObject(ListElementDto element) {
        return new ListElement(element.getValue());
    }

    @Override
    public ListElementDto convertToDtoObject(ListElement element) {
        return new ListElementDto(element.getId(), element.getValue());
    }
}
