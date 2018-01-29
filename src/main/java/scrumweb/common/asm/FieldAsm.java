package scrumweb.common.asm;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import scrumweb.dto.CheckBoxDto;
import scrumweb.dto.ProjectFieldDto;
import scrumweb.dto.RadioButtonDto;
import scrumweb.project.field.CheckBox;
import scrumweb.project.field.CheckBoxContainer;
import scrumweb.project.field.InputField;
import scrumweb.project.field.ProjectField;
import scrumweb.project.field.RadioButton;
import scrumweb.project.field.RadioButtonContainer;
import scrumweb.project.field.TextArea;
import scrumweb.project.repository.CheckBoxRepository;
import scrumweb.project.field.ProjectField.FieldType;
import scrumweb.project.repository.RadioButtonRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class FieldAsm {

    private CheckBoxRepository checkBoxRepository;
    private RadioButtonRepository radioButtonRepository;

    public ProjectField createCheckBoxContainer(ProjectFieldDto projectFieldDto) {
        Set<CheckBox> checkBoxes = projectFieldDto.getCheckBoxContainer().getCheckBoxes().stream().map(FieldAsm::createCheckBox).collect(Collectors.toSet());
        checkBoxRepository.save(checkBoxes);
        return new CheckBoxContainer(FieldType.getFieldType(projectFieldDto.getFieldType()), projectFieldDto.getFieldName(), projectFieldDto.getIsRequired(), checkBoxes);
    }

    public ProjectField createRadioButtonContainer(ProjectFieldDto projectFieldDto) {
        Set<RadioButton> radioButtons = projectFieldDto.getRadioButtonContainerDto().getRadioButtonDtos().stream().map(FieldAsm::createRadioButton).collect(Collectors.toSet());
        radioButtonRepository.save(radioButtons);
        return new RadioButtonContainer(FieldType.getFieldType(projectFieldDto.getFieldType()), projectFieldDto.getFieldName(), projectFieldDto.getIsRequired(), radioButtons);
    }

    public ProjectField createInputField(ProjectFieldDto projectFieldDto) {
        return new InputField(
                FieldType.getFieldType(projectFieldDto.getFieldType()),
                projectFieldDto.getFieldName(),
                projectFieldDto.getIsRequired(),
                projectFieldDto.getTextField().getMinCharacters(),
                projectFieldDto.getTextField().getMaxCharacters()
        );
    }

    public ProjectField createTextArea(ProjectFieldDto projectFieldDto) {
        return new TextArea(
                FieldType.getFieldType(projectFieldDto.getFieldType()),
                projectFieldDto.getFieldName(),
                projectFieldDto.getIsRequired(),
                projectFieldDto.getTextField().getMinCharacters(),
                projectFieldDto.getTextField().getMaxCharacters()
        );
    }

    private static RadioButton createRadioButton(RadioButtonDto radioButtonDto) {
        return new RadioButton(radioButtonDto.getValue());
    }
    private static CheckBox createCheckBox(CheckBoxDto checkBoxDto) {
        return new CheckBox(checkBoxDto.getValue());
    }
}
