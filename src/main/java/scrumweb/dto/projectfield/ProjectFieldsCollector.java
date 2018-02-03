package scrumweb.dto.projectfield;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import scrumweb.common.FieldsExtractor;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class ProjectFieldsCollector implements FieldsExtractor<ProjectFieldDto, ProjectFieldsCollector> {

    private Set<CheckBoxContainerDto> checkBoxContainerDtos;
    private Set<InputFieldDto> inputFieldDtos;
    private Set<ListElementsContainerDto> listElementsContainerDtos;
    private Set<RadioButtonContainerDto> radioButtonContainerDtos;
    private Set<TextAreaDto> textAreaDtos;

    @Override
    public Set<ProjectFieldDto> extractFields(ProjectFieldsCollector fieldsColletor) {
        Set<ProjectFieldDto> projectFieldDtos = new HashSet<>();
        projectFieldDtos.addAll(fieldsColletor.getCheckBoxContainerDtos());
        projectFieldDtos.addAll(fieldsColletor.getInputFieldDtos());
        projectFieldDtos.addAll(fieldsColletor.getListElementsContainerDtos());
        projectFieldDtos.addAll(fieldsColletor.getRadioButtonContainerDtos());
        projectFieldDtos.addAll(fieldsColletor.getTextAreaDtos());
        return projectFieldDtos;
    }
}
