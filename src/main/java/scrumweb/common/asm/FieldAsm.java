package scrumweb.common.asm;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import scrumweb.dto.CheckBoxContainerDto;
import scrumweb.dto.CheckBoxDto;
import scrumweb.dto.ProjectFieldDto;
import scrumweb.dto.RadioButtonDto;
import scrumweb.dto.InputFieldDto;
import scrumweb.issue.field.CheckBoxContent;
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
                    projectField.getId(),
                    projectField.getFieldType().toString(),
                    projectField.getName(),
                    projectField.getIsRequired(), null, null,
                    createTextField(projectField, ((InputFieldContent) fieldContent))
            );
        }else if (fieldContent instanceof CheckBoxContent) {
            return new ProjectFieldDto(
                    projectField.getId(),
                    projectField.getFieldType().toString(),
                    projectField.getName(),
                    projectField.getIsRequired(),
                    new CheckBoxContainerDto(projectField.getId(), createCheckBoxDto(((CheckBoxContent) fieldContent).getCheckBoxes(), true)),
                    null, null);
        }
        return null;
    }

    public FieldContent createCheckBoxContent(ProjectField projectField, CheckBoxContainerDto checkBoxContainerDto) {
        Set<CheckBox> checkBoxes = checkBoxContainerDto.getCheckBoxes().stream().map(checkBox -> checkBoxRepository.getOne(checkBox.getId())).collect(Collectors.toSet());
        return new CheckBoxContent(projectField, checkBoxes);
    }

    public FieldContent createFieldContentInputField(InputFieldDto inputFieldDto, ProjectField projectField) {
        return new InputFieldContent(projectField, inputFieldDto.getContent());
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
                projectFieldDto.getInputField().getMinCharacters(),
                projectFieldDto.getInputField().getMaxCharacters()
        );
    }

    public ProjectField createTextArea(ProjectFieldDto projectFieldDto) {
        return new TextArea(
                FieldType.getFieldType(projectFieldDto.getFieldType()),
                projectFieldDto.getFieldName(),
                projectFieldDto.getIsRequired(),
                projectFieldDto.getInputField().getMinCharacters(),
                projectFieldDto.getInputField().getMaxCharacters()
        );
    }

    private InputFieldDto createTextField(ProjectField projectField, InputFieldContent inputFieldContent) {
        return new InputFieldDto(projectField.getId(), inputFieldContent.getContent(), ((InputField) projectField).getMaxCharacters(), ((InputField) projectField).getMinCharacters());
    }

    public Set<CheckBoxDto> createCheckBoxDto(Set<CheckBox> checkBoxes, Boolean isSelected) {
        return checkBoxes.stream()
                .map(checkBox -> new CheckBoxDto(checkBox.getId(), isSelected, checkBox.getValue()))
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
