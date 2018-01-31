package scrumweb.common.asm;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import scrumweb.dto.CheckBoxContainerDto;
import scrumweb.dto.CheckBoxDto;
import scrumweb.dto.ListElementDto;
import scrumweb.dto.ListElementsContainerDto;
import scrumweb.dto.ProjectFieldDto;
import scrumweb.dto.RadioButtonContainerDto;
import scrumweb.dto.RadioButtonDto;
import scrumweb.dto.InputFieldDto;
import scrumweb.dto.TextAreaDto;
import scrumweb.issue.field.CheckBoxContent;
import scrumweb.issue.field.FieldContent;
import scrumweb.issue.field.InputFieldContent;
import scrumweb.issue.field.ListContent;
import scrumweb.issue.field.RadioButtonContent;
import scrumweb.issue.field.TextAreaContent;
import scrumweb.project.domain.Project;
import scrumweb.project.field.CheckBox;
import scrumweb.project.field.CheckBoxContainer;
import scrumweb.project.field.InputField;
import scrumweb.project.field.ListElement;
import scrumweb.project.field.ListElementsContainer;
import scrumweb.project.field.ProjectField;
import scrumweb.project.field.RadioButton;
import scrumweb.project.field.RadioButtonContainer;
import scrumweb.project.field.TextArea;
import scrumweb.project.repository.CheckBoxRepository;
import scrumweb.project.field.ProjectField.FieldType;
import scrumweb.project.repository.ListElementRepository;
import scrumweb.project.repository.RadioButtonRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class FieldAsm {

    private CheckBoxRepository checkBoxRepository;
    private RadioButtonRepository radioButtonRepository;
    private ListElementRepository listElementRepository;

    public ProjectField createCheckBoxContainer(CheckBoxContainerDto checkBoxContainerDto) {
        Set<CheckBox> checkBoxes = checkBoxContainerDto.getCheckBoxes().stream().map(this::createCheckBox).collect(Collectors.toSet());
        checkBoxRepository.save(checkBoxes);
        return new CheckBoxContainer(FieldType.getFieldType(checkBoxContainerDto.getFieldType()), checkBoxContainerDto.getFieldName(), checkBoxContainerDto.getIsRequired(), checkBoxes);
    }

    public ProjectField createListContainer(ListElementsContainerDto listElementsContainerDto) {
        Set<ListElement> listElements = listElementsContainerDto.getListElements().stream().map(this::createListlement).collect(Collectors.toSet());
        listElementRepository.save(listElements);
        return new ListElementsContainer(FieldType.getFieldType(listElementsContainerDto.getFieldType()), listElementsContainerDto.getFieldName(), listElementsContainerDto.getIsRequired(), listElements);
    }

    public ProjectFieldDto createProjectFieldDto(FieldContent fieldContent) {
        ProjectField projectField = fieldContent.getProjectField();
        if (fieldContent instanceof InputFieldContent) {
            return createInputFieldDto(((InputFieldContent) fieldContent));
        } else if (fieldContent instanceof CheckBoxContent) {
            return new CheckBoxContainerDto(
                    projectField.getId(),
                    projectField.getFieldType().toString(),
                    projectField.getName(),
                    projectField.getIsRequired(),
                    createCheckBoxDto(((CheckBoxContent) fieldContent).getCheckBoxes(), true)
            );
        }
    }

    public FieldContent createCheckBoxContent(ProjectField projectField, CheckBoxContainerDto checkBoxContainerDto) {
        Set<CheckBox> checkBoxes = checkBoxContainerDto.getCheckBoxes().stream().map(checkBox -> checkBoxRepository.getOne(checkBox.getId())).collect(Collectors.toSet());
        return new CheckBoxContent(projectField, checkBoxes);
    }

    public FieldContent createFieldContentInputField(InputFieldDto inputFieldDto, ProjectField projectField) {
        return new InputFieldContent(projectField, inputFieldDto.getContent());
    }

    public ProjectField createRadioButtonContainer(RadioButtonContainerDto radioButtonContainerDto) {
        Set<RadioButton> radioButtons = radioButtonContainerDto.getRadioButtons().stream().map(this::createRadioButton).collect(Collectors.toSet());
        radioButtonRepository.save(radioButtons);
        return new RadioButtonContainer(FieldType.getFieldType(radioButtonContainerDto.getFieldType()), radioButtonContainerDto.getFieldName(), radioButtonContainerDto.getIsRequired(), radioButtons);
    }

    public ProjectField createInputField(InputFieldDto inputFieldDto) {
        return new InputField(
                FieldType.getFieldType(inputFieldDto.getFieldType()),
                inputFieldDto.getFieldName(),
                inputFieldDto.getIsRequired(),
                inputFieldDto.getMinCharacters(),
                inputFieldDto.getMaxCharacters()
        );
    }

    public ProjectField createTextArea(TextAreaDto textAreaDto) {
        return new TextArea(
                FieldType.getFieldType(textAreaDto.getFieldType()),
                textAreaDto.getFieldName(),
                textAreaDto.getIsRequired(),
                textAreaDto.getMinCharacters(),
                textAreaDto.getMaxCharacters()
        );
    }

    private InputFieldDto createInputFieldDto(InputFieldContent inputFieldContent) {
        ProjectField projectField = inputFieldContent.getProjectField();
        return new InputFieldDto(
                projectField.getId(),
                projectField.getFieldType().toString(),
                projectField.getName(),
                projectField.getIsRequired(),
                inputFieldContent.getContent(),
                ((InputField) projectField).getMaxCharacters(),
                ((InputField) projectField).getMinCharacters()
        );
    }

    public Set<CheckBoxDto> createCheckBoxDto(Set<CheckBox> checkBoxes, Boolean isSelected) {
        return checkBoxes.stream()
                .map(checkBox -> new CheckBoxDto(checkBox.getId(), isSelected, checkBox.getValue()))
                .collect(Collectors.toSet());
    }

    public Set<ListElementDto> createListElementDto(Set<ListElement> listElements, Boolean isSelected) {
        return listElements.stream()
                .map(element -> new ListElementDto(element.getId(), isSelected, element.getValue()))
                .collect(Collectors.toSet());
    }

    private Set<RadioButtonDto> createRadioButtonDto(Set<RadioButton> radioButtons, Boolean isSelected) {
        return radioButtons.stream()
                .map(radioButton -> new RadioButtonDto(radioButton.getId(), radioButton.getValue(), isSelected))
                .collect(Collectors.toSet());
    }

    private RadioButton createRadioButton(RadioButtonDto radioButtonDto) {
        return new RadioButton(radioButtonDto.getValue());
    }

    private CheckBox createCheckBox(CheckBoxDto checkBoxDto) {
        return new CheckBox(checkBoxDto.getValue());
    }

    private ListElement createListlement(ListElementDto listElementDto) {
        return new ListElement(listElementDto.getValue());
    }

    private TextAreaDto createTextAreaDto(TextAreaContent textAreaContent) {
        ProjectField projectField = textAreaContent.getProjectField();
        return new TextAreaDto(
                projectField.getId(),
                projectField.getFieldType().toString(),
                projectField.getName(),
                projectField.getIsRequired(),
                textAreaContent.getContent(),
                ((TextArea) projectField).getMaxCharacters(),
                ((TextArea) projectField).getMinCharacters());
    }

    public RadioButtonContainerDto createCheckBoxContainerDto(RadioButtonContainer radioButtonContainer, Set<RadioButton> radioButtons) {
        return new RadioButtonContainerDto(
                radioButtonContainer.getId(),
                radioButtonContainer.getFieldType().toString(),
                radioButtonContainer.getName(),
                radioButtonContainer.getIsRequired(),
                createRadioButtonDto(radioButtons, true));
    }

    public ListElementsContainerDto createListElementsContainerDto(ListElementsContainer listElementsContainer) {
        return new ListElementsContainerDto(
                listElementsContainer.getId(),
                listElementsContainer.getFieldType().toString(),
                listElementsContainer.getName(),
                listElementsContainer.getIsRequired(),
                createListElementDto(listElementsContainer.getElements(), true));
    }
}
