package scrumweb.common.asm.fieldcontent;

import scrumweb.dto.fieldcontent.FieldContentDto;
import scrumweb.issue.fieldcontent.FieldContent;
import scrumweb.projectfield.domain.ProjectField;

public interface FieldContentAsm<T extends FieldContent, Q extends FieldContentDto, P extends ProjectField> {
    T createEntityObject(P projectField, Q fieldDto);
    Q createDtoObject(T fieldContent);
}
