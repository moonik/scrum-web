package scrumweb.common.asm.projectfield;

import scrumweb.dto.projectfield.ProjectFieldDto;
import scrumweb.projectfield.domain.ProjectField;


public interface ProjectFieldAsm<T extends ProjectField, Q extends ProjectFieldDto>{
    T createEntityObject(Q projectFieldDto);
    Q createDtoObject(T projectField);
    String createHtml(Q projectFieldDto);
}
