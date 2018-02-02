package scrumweb.common.asm.projectfield;

import scrumweb.dto.projectfield.ProjectFieldDto;
import scrumweb.projectfield.domain.ProjectField;

public interface ProjectFieldAsm<T extends ProjectField, Q extends ProjectFieldDto>{
    T convertToEntityObject(Q projectFieldDto);
    Q convertToDtoObject(T projectField);
}
