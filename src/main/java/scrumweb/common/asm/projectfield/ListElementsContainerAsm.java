package scrumweb.common.asm.projectfield;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import scrumweb.common.asm.common.FieldElementsAsm;
import scrumweb.dto.projectfield.ListElementDto;
import scrumweb.dto.projectfield.ListElementsContainerDto;
import scrumweb.projectfield.domain.ListElement;
import scrumweb.projectfield.domain.ListElementsContainer;
import scrumweb.projectfield.domain.ProjectField.FieldType;
import scrumweb.projectfield.repository.ListElementRepository;
import scrumweb.projectfield.repository.RadioButtonRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ListElementsContainerAsm implements ProjectFieldAsm<ListElementsContainer, ListElementsContainerDto> {

    private ListElementRepository listElementRepository;
    private FieldElementsAsm<ListElement, ListElementDto> fieldElementsAsm;

    @Override
    public ListElementsContainer createEntityObject(ListElementsContainerDto projectFieldDto) {
        Set<ListElement> listElements = projectFieldDto.getListElements().stream().map(listElementDto -> fieldElementsAsm.convertToEntityObject(listElementDto)).collect(Collectors.toSet());
        listElementRepository.save(listElements);
        return new ListElementsContainer(FieldType.getFieldType(projectFieldDto.getFieldType()), projectFieldDto.getFieldName(), projectFieldDto.getIsRequired(), listElements);
    }

    @Override
    public ListElementsContainerDto createDtoObject(ListElementsContainer projectField) {
        Set<ListElementDto> listElements = projectField.getElements().stream().map(listElement -> fieldElementsAsm.convertToDtoObject(listElement)).collect(Collectors.toSet());
        return new ListElementsContainerDto(
                projectField.getId(),
                projectField.getFieldType().toString(),
                projectField.getName(),
                projectField.getIsRequired(),
                listElements
        );
    }
}
