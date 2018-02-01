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
import java.util.List;
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

    public FieldContent createCheckBoxContent(ProjectField projectField, CheckBoxContainerDto checkBoxContainerDto) {
        Set<CheckBox> checkBoxes = checkBoxContainerDto.getCheckBoxes().stream().map(checkBox -> checkBoxRepository.getOne(checkBox.getId())).collect(Collectors.toSet());
        return new CheckBoxContent(projectField, checkBoxes);
    }

    public FieldContent createRadioButtonContent(ProjectField projectField, RadioButtonContainerDto radioButtonContainer) {
        List<RadioButton> radioButtons = radioButtonContainer.getRadioButtons().stream().map(radioButton -> radioButtonRepository.getOne(radioButton.getId())).collect(Collectors.toList());
        return new RadioButtonContent(projectField, radioButtons.get(0));
    }

    public FieldContent createTextAreaContent(ProjectField projectField, TextAreaDto textAreaDto) {
        return new TextAreaContent(projectField, textAreaDto.getContent());
    }

    public FieldContent createListContent(ProjectField projectField, ListElementsContainerDto listElementsContainerDto) {
        Set<ListElement> listElements = listElementsContainerDto.getListElements().stream().map(l -> listElementRepository.getOne(l.getId())).collect(Collectors.toSet());
        return new ListContent(projectField, listElements);
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

    public InputFieldDto createInputFieldDtoConent(InputFieldContent inputFieldContent) {
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

    private Set<CheckBoxDto> createCheckBoxDto(Set<CheckBox> checkBoxes, Boolean isSelected) {
        return checkBoxes.stream()
                .map(checkBox -> new CheckBoxDto(checkBox.getId(), isSelected, checkBox.getValue()))
                .collect(Collectors.toSet());
    }

    private Set<ListElementDto> createListElementDto(Set<ListElement> listElements, Boolean isSelected) {
        return listElements.stream()
                .map(element -> new ListElementDto(element.getId(), isSelected, element.getValue()))
                .collect(Collectors.toSet());
    }

    private RadioButtonDto createRadioButtonDto(RadioButton radioButton, Boolean isSelected) {
        return new RadioButtonDto(radioButton.getId(), radioButton.getValue(), isSelected);
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

    public TextAreaDto createTextAreaDtoContent(TextAreaContent textAreaContent) {
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

    public RadioButtonContainerDto createRadioButtonContainerDtoContent(RadioButtonContent radioButtonContent) {
        RadioButtonDto radioButtonDto = createRadioButtonDto(radioButtonContent.getRadioButton(), true);
        Set<RadioButtonDto> radioButtonDtos = new HashSet<>(Arrays.asList(radioButtonDto));
        return new RadioButtonContainerDto(
                radioButtonContent.getId(),
                radioButtonContent.getProjectField().getFieldType().toString(),
                radioButtonContent.getProjectField().getName(),
                radioButtonContent.getProjectField().getIsRequired(),
                radioButtonDtos
        );
    }

    public CheckBoxContainerDto createCheckBoxContainerDtoContent(CheckBoxContent checkBoxContent) {
        ProjectField projectField = checkBoxContent.getProjectField();
        return new CheckBoxContainerDto(
                checkBoxContent.getId(),
                projectField.getFieldType().toString(),
                projectField.getName(),
                projectField.getIsRequired(),
                createCheckBoxDto(checkBoxContent.getCheckBoxes(), true)
        );
    }

    public ListElementsContainerDto createListElementsContainerDtoContent(ListContent listContent) {
        ProjectField projectField = listContent.getProjectField();
        return new ListElementsContainerDto(
                projectField.getId(),
                projectField.getFieldType().toString(),
                projectField.getName(),
                projectField.getIsRequired(),
                createListElementDto(listContent.getListElements(), true));
    }
}
