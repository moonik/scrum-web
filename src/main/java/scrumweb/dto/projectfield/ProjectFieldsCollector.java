package scrumweb.dto.projectfield;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import scrumweb.common.FieldsExtractor;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Component
@Getter @Setter
public class ProjectFieldsCollector implements FieldsExtractor<ProjectFieldDto> {

    private Set<CheckBoxContainerDto> checkBoxContainerDtos;
    private Set<InputFieldDto> inputFieldDtos;
    private Set<ListElementsContainerDto> listElementsContainerDtos;
    private Set<RadioButtonContainerDto> radioButtonContainerDtos;
    private Set<TextAreaDto> textAreaDtos;

    @Override
    public Set<ProjectFieldDto> extractFields() {
        Set<ProjectFieldDto> projectFieldDtos = new HashSet<>();
        projectFieldDtos.addAll(checkBoxContainerDtos);
        projectFieldDtos.addAll(inputFieldDtos);
        projectFieldDtos.addAll(listElementsContainerDtos);
        projectFieldDtos.addAll(radioButtonContainerDtos);
        projectFieldDtos.addAll(textAreaDtos);
        return projectFieldDtos;
    }
}
