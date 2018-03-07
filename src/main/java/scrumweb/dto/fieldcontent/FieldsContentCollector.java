package scrumweb.dto.fieldcontent;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import scrumweb.common.FieldsExtractor;
import scrumweb.dto.projectfield.ProjectFieldDto;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Component
public class FieldsContentCollector implements FieldsExtractor<FieldContentDto> {

    private Set<CheckBoxContainerContentDto> checkBoxContainerContentDtos;
    private Set<InputFieldContentDto> inputFieldContentDtos;
    private Set<ListElementsContainerContentDto> listElementsContainerContentDtos;
    private Set<RadioButtonContainerContentDto> radioButtonContainerContentDtos;
    private Set<TextAreaContentDto> textAreaContentDtos;

    @Override
    public Set<FieldContentDto> extractFields() {
        Set<FieldContentDto> projectFieldDtos = new HashSet<>();
        projectFieldDtos.addAll(checkBoxContainerContentDtos);
        projectFieldDtos.addAll(inputFieldContentDtos);
        projectFieldDtos.addAll(listElementsContainerContentDtos);
        projectFieldDtos.addAll(radioButtonContainerContentDtos);
        projectFieldDtos.addAll(textAreaContentDtos);
        return projectFieldDtos;
    }
}
