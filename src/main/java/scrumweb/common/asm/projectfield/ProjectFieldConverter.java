package scrumweb.common.asm.projectfield;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import scrumweb.dto.projectfield.CheckBoxContainerDto;
import scrumweb.dto.projectfield.InputFieldDto;
import scrumweb.dto.projectfield.ListElementsContainerDto;
import scrumweb.dto.projectfield.ProjectFieldDto;
import scrumweb.dto.projectfield.RadioButtonContainerDto;
import scrumweb.dto.projectfield.TextAreaDto;
import scrumweb.projectfield.domain.CheckBoxContainer;
import scrumweb.projectfield.domain.InputField;
import scrumweb.projectfield.domain.ListElementsContainer;
import scrumweb.projectfield.domain.ProjectField;
import scrumweb.projectfield.domain.RadioButton;
import scrumweb.projectfield.domain.RadioButtonContainer;
import scrumweb.projectfield.domain.TextArea;

@Component
@AllArgsConstructor
public class ProjectFieldConverter {

    private CheckBoxContainerAsm checkBoxContainerAsm;
    private InputFieldAsm inputFieldAsm;
    private ListElementsContainerAsm listElementsContainerAsm;
    private RadioButtonContainerAsm radioButtonContainerAsm;
    private TextAreaAsm textAreaAsm;

    public ProjectField createEntityObject(ProjectFieldDto projectFieldDto) {
        if (projectFieldDto instanceof CheckBoxContainerDto) {
            return checkBoxContainerAsm.createEntityObject(((CheckBoxContainerDto) projectFieldDto));
        } else if (projectFieldDto instanceof InputFieldDto) {
            return inputFieldAsm.createEntityObject(((InputFieldDto) projectFieldDto));
        } else if (projectFieldDto instanceof ListElementsContainerDto) {
            return listElementsContainerAsm.createEntityObject(((ListElementsContainerDto) projectFieldDto));
        } else if (projectFieldDto instanceof RadioButtonContainerDto) {
            return radioButtonContainerAsm.createEntityObject(((RadioButtonContainerDto) projectFieldDto));
        } else
            return textAreaAsm.createEntityObject(((TextAreaDto) projectFieldDto));
    }

    public ProjectFieldDto createDtoObject(ProjectField projectField) {
        if (projectField instanceof CheckBoxContainer) {
            return checkBoxContainerAsm.createDtoObject(((CheckBoxContainer) projectField));
        } else if (projectField instanceof InputField) {
            return inputFieldAsm.createDtoObject(((InputField) projectField));
        } else if (projectField instanceof ListElementsContainer) {
            return listElementsContainerAsm.createDtoObject(((ListElementsContainer) projectField));
        } else if (projectField instanceof RadioButtonContainer) {
            return radioButtonContainerAsm.createDtoObject(((RadioButtonContainer) projectField));
        } else
            return textAreaAsm.createDtoObject(((TextArea) projectField));
    }
}
