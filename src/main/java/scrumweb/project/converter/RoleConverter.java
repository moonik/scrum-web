package scrumweb.project.converter;

import scrumweb.project.domain.ProjectMember.Role;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class RoleConverter implements AttributeConverter<Role, String> {

    @Override
    public String convertToDatabaseColumn(Role role) {
        return role.getRoleString();
    }

    @Override
    public Role convertToEntityAttribute(String s) {
        return Role.getRole(s);
    }
}
