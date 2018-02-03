package scrumweb.common.asm.fieldcontent;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import scrumweb.common.asm.common.FieldElementsAsm;
import scrumweb.dto.fieldcontent.ListElementsContainerContentDto;
import scrumweb.dto.projectfield.ListElementDto;
import scrumweb.issue.fieldcontent.ListContent;
import scrumweb.projectfield.domain.ListElement;
import scrumweb.projectfield.domain.ListElementsContainer;
import scrumweb.projectfield.repository.FieldElementsRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ListContentAsm implements FieldContentAsm<ListContent, ListElementsContainerContentDto, ListElementsContainer> {

    private FieldElementsAsm<ListElement, ListElementDto> fieldElementsAsm;
    private FieldElementsRepository<ListElement> listElementRepository;

    @Override
    public ListContent createEntityObject(ListElementsContainer projectField, ListElementsContainerContentDto fieldContentDto) {
        return new ListContent(projectField, listElementRepository.getElements(extractIds(fieldContentDto.getListElements())));
    }

    @Override
    public ListElementsContainerContentDto createDtoObject(ListContent fieldContent) {
        Set<ListElementDto> listElementsDto = fieldContent.getListElements().stream().map(listElement -> fieldElementsAsm.convertToDtoObject(listElement)).collect(Collectors.toSet());
        return new ListElementsContainerContentDto(fieldContent.getProjectField().getId(), listElementsDto);
    }

    private Set<Long> extractIds(Set<ListElementDto> listElementDtos) {
        return listElementDtos.stream()
                .map(ListElementDto::getId)
                .collect(Collectors.toSet());
    }
}
