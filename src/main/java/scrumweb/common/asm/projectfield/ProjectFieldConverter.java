package scrumweb.common.asm.projectfield;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import scrumweb.dto.projectfield.*;
import scrumweb.projectfield.domain.*;

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
