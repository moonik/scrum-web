package scrumweb.common.asm.projectfield;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import scrumweb.dto.projectfield.ListElementDto;
import scrumweb.dto.projectfield.ListElementsContainerDto;
import scrumweb.projectfield.domain.ListElement;
import scrumweb.projectfield.domain.ListElementsContainer;
import scrumweb.projectfield.domain.ProjectField.FieldType;
import scrumweb.projectfield.repository.ListElementRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ListElementsContainerAsm implements ProjectFieldAsm<ListElementsContainer, ListElementsContainerDto> {

    private ListElementRepository listElementRepository;

    @Override
    public ListElementsContainer convertToEntityObject(ListElementsContainerDto projectFieldDto) {
        Set<ListElement> listElements = projectFieldDto.getListElements().stream().map(this::createListlement).collect(Collectors.toSet());
        listElementRepository.save(listElements);
        return new ListElementsContainer(FieldType.getFieldType(projectFieldDto.getFieldType()), projectFieldDto.getFieldName(), projectFieldDto.getIsRequired(), listElements);
    }

    @Override
    public ListElementsContainerDto convertToDtoObject(ListElementsContainer projectField) {
        return new ListElementsContainerDto(
                projectField.getId(),
                projectField.getFieldType().toString(),
                projectField.getName(),
                projectField.getIsRequired(),
                createListElementDto(projectField.getElements()));
    }

    private ListElement createListlement(ListElementDto listElementDto) {
        return new ListElement(listElementDto.getValue());
    }

    private Set<ListElementDto> createListElementDto(Set<ListElement> listElements) {
        return listElements.stream()
                .map(element -> new ListElementDto(element.getId(), element.getValue()))
                .collect(Collectors.toSet());
    }
}
