package scrumweb.dto.fieldcontent;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import scrumweb.common.FieldsExtractor;
import scrumweb.dto.projectfield.ProjectFieldDto;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class FieldsContentCollector implements FieldsExtractor<FieldContentDto, FieldsContentCollector> {

    private Set<CheckBoxContainerContentDto> checkBoxContainerContentDtos;
    private Set<InputFieldContentDto> inputFieldContentDtos;
    private Set<ListElementsContainerContentDto> listElementsContainerContentDtos;
    private Set<RadioButtonContainerContentDto> radioButtonContainerContentDtos;
    private Set<TextAreaContentDto> textAreaContentDtos;

    @Override
    public Set<FieldContentDto> extractFields(FieldsContentCollector fieldsCollector) {
        Set<FieldContentDto> projectFieldDtos = new HashSet<>();
        projectFieldDtos.addAll(fieldsCollector.getCheckBoxContainerContentDtos());
        projectFieldDtos.addAll(fieldsCollector.getInputFieldContentDtos());
        projectFieldDtos.addAll(fieldsCollector.getListElementsContainerContentDtos());
        projectFieldDtos.addAll(fieldsCollector.getRadioButtonContainerContentDtos());
        projectFieldDtos.addAll(fieldsCollector.getTextAreaContentDtos());
        return projectFieldDtos;
    }
}
