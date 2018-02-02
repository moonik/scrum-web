package scrumweb.common.asm.projectfield;

import scrumweb.dto.projectfield.CheckBoxDto;
import scrumweb.dto.projectfield.ProjectFieldDto;
import scrumweb.projectfield.domain.CheckBox;
import scrumweb.projectfield.domain.ProjectField;

import java.util.Set;
import java.util.stream.Collectors;

public interface ProjectFieldAsm<T extends ProjectField, Q extends ProjectFieldDto>{
    T convertToEntityObject(Q projectFieldDto);
    Q convertToDtoObject(T projectField);
}
