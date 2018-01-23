package scrumweb.user.project.converter;

import scrumweb.user.project.domain.Project;
import scrumweb.user.project.domain.ProjectMember;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;
import javax.persistence.Converter;

@Converter
public class RoleConverter implements AttributeConverter<ProjectMember.Role, String> {

    @Override
    public String convertToDatabaseColumn(ProjectMember.Role role) {
        return role.getRoleString();
    }

    @Override
    public ProjectMember.Role convertToEntityAttribute(String s) {
        return ProjectMember.Role.getRole(s);
    }
}
