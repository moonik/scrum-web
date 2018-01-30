package scrumweb.common.asm;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import scrumweb.dto.CheckBoxContainerDto;
import scrumweb.dto.CheckBoxDto;
import scrumweb.dto.ProjectFieldDto;
import scrumweb.dto.RadioButtonDto;
import scrumweb.dto.TextField;
import scrumweb.issue.field.FieldContent;
import scrumweb.issue.field.InputFieldContent;
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
        Set<CheckBox> checkBoxes = projectFieldDto.getCheckBoxContainer().getCheckBoxes().stream().map(this::createCheckBox).collect(Collectors.toSet());
        checkBoxRepository.save(checkBoxes);
        return new CheckBoxContainer(FieldType.getFieldType(projectFieldDto.getFieldType()), projectFieldDto.getFieldName(), projectFieldDto.getIsRequired(), checkBoxes);
    }

    public ProjectFieldDto createProjectFieldDto(ProjectField projectField, FieldContent fieldContent) {
        if (fieldContent instanceof InputFieldContent) {
            return new ProjectFieldDto(
                    projectField.getFieldType().toString(),
                    projectField.getName(),
                    projectField.getIsRequired(), null, null,
                    createTextField(projectField, ((InputFieldContent) fieldContent))
            );
        }
        //TODO add more fields
        return null;
    }

    public FieldContent createFieldContentInputField(TextField textField, ProjectField projectField) {
        return new InputFieldContent(projectField, textField.getContent());
    }

    public ProjectField createRadioButtonContainer(ProjectFieldDto projectFieldDto) {
        Set<RadioButton> radioButtons = projectFieldDto.getRadioButtonContainerDto().getRadioButtonDtos().stream().map(this::createRadioButton).collect(Collectors.toSet());
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

    private TextField createTextField(ProjectField projectField, InputFieldContent inputFieldContent) {
        return new TextField(projectField.getId(), false, inputFieldContent.getContent(), ((InputField) projectField).getMaxCharacters(), ((InputField) projectField).getMinCharacters());
    }

    private Set<CheckBoxDto> createCheckBoxDto(CheckBoxContainer checkBoxContainer) {
        return checkBoxContainer.getCheckBoxes().stream()
                .map(checkBox -> new CheckBoxDto(checkBox.getId(), checkBox.getValue()))
                .collect(Collectors.toSet());
    }

    private Set<RadioButtonDto> createRadioButtonDto(RadioButtonContainer radioButtonContainer) {
        return radioButtonContainer.getRadioButtons().stream()
                .map(radioButton -> new RadioButtonDto(radioButton.getId(), radioButton.getValue()))
                .collect(Collectors.toSet());
    }

    private RadioButton createRadioButton(RadioButtonDto radioButtonDto) {
        return new RadioButton(radioButtonDto.getValue());
    }

    private CheckBox createCheckBox(CheckBoxDto checkBoxDto) {
        return new CheckBox(checkBoxDto.getValue());
    }
}
