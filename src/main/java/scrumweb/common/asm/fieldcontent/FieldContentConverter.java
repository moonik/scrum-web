package scrumweb.common.asm.fieldcontent;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import scrumweb.dto.fieldcontent.CheckBoxContainerContentDto;
import scrumweb.dto.fieldcontent.FieldContentDto;
import scrumweb.dto.fieldcontent.InputFieldContentDto;
import scrumweb.dto.fieldcontent.ListElementsContainerContentDto;
import scrumweb.dto.fieldcontent.RadioButtonContainerContentDto;
import scrumweb.dto.fieldcontent.TextAreaContentDto;
import scrumweb.issue.fieldcontent.CheckBoxContent;
import scrumweb.issue.fieldcontent.FieldContent;
import scrumweb.issue.fieldcontent.InputFieldContent;
import scrumweb.issue.fieldcontent.ListContent;
import scrumweb.issue.fieldcontent.RadioButtonContent;
import scrumweb.issue.fieldcontent.TextAreaContent;
import scrumweb.projectfield.domain.CheckBoxContainer;
import scrumweb.projectfield.domain.InputField;
import scrumweb.projectfield.domain.ListElementsContainer;
import scrumweb.projectfield.domain.ProjectField;
import scrumweb.projectfield.domain.RadioButtonContainer;
import scrumweb.projectfield.domain.TextArea;

@Component
@AllArgsConstructor
public class FieldContentConverter {

    private CheckBoxContentAsm checkBoxContentAsm;
    private InputFieldContentAsm inputFieldContentAsm;
    private ListContentAsm listContentAsm;
    private RadioButtonContentAsm radioButtonContentAsm;
    private TextAreaContentAsm textAreaContentAsm;

    public FieldContent createObjectEntity(ProjectField projectField, FieldContentDto fieldContent) {
        if (fieldContent instanceof CheckBoxContainerContentDto) {
            return checkBoxContentAsm.createEntityObject(((CheckBoxContainer) projectField), ((CheckBoxContainerContentDto) fieldContent));
        } else if (fieldContent instanceof InputFieldContentDto) {
            return inputFieldContentAsm.createEntityObject(((InputField) projectField), ((InputFieldContentDto) fieldContent));
        } else if (fieldContent instanceof ListElementsContainerContentDto) {
            return listContentAsm.createEntityObject(((ListElementsContainer) projectField), ((ListElementsContainerContentDto) fieldContent));
        } else if (fieldContent instanceof RadioButtonContainerContentDto) {
            return radioButtonContentAsm.createEntityObject(((RadioButtonContainer) projectField), ((RadioButtonContainerContentDto) fieldContent));
        }  else
            return textAreaContentAsm.createEntityObject(((TextArea) projectField), ((TextAreaContentDto) fieldContent));
    }

    public FieldContentDto createDtoObject(FieldContent fieldContent) {
        if (fieldContent instanceof CheckBoxContent) {
            return checkBoxContentAsm.createDtoObject(((CheckBoxContent) fieldContent));
        } else if (fieldContent instanceof InputFieldContent) {
            return inputFieldContentAsm.createDtoObject(((InputFieldContent) fieldContent));
        } else if (fieldContent instanceof ListContent) {
            return listContentAsm.createDtoObject(((ListContent) fieldContent));
        } else if (fieldContent instanceof RadioButtonContent) {
            return radioButtonContentAsm.createDtoObject(((RadioButtonContent) fieldContent));
        } else
            return textAreaContentAsm.createDtoObject(((TextAreaContent) fieldContent));
    }
}
