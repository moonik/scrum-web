package scrumweb.common.asm;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import scrumweb.dto.issue.IssueDto;
import scrumweb.dto.project.ProjectDetailsDto;
import scrumweb.dto.project.ProjectDto;
import scrumweb.dto.project.ProjectMemberDto;
import scrumweb.dto.user.UserProfileDto;
import scrumweb.project.domain.Project;
import scrumweb.project.domain.ProjectMember;
import scrumweb.project.domain.ProjectMember.Role;
import scrumweb.user.account.domain.UserAccount;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ProjectAsm {

    public static Project makeProject(ProjectDto projectDto) {
        return new Project(projectDto.getName(), projectDto.getDescription(), projectDto.getIcon(), projectDto.getProjectKey());
    }

    public static ProjectDto makeProjectDto(Project project) {
        return ProjectDto.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .icon(project.getIcon())
                .members(convertProjectMembers(project, project.getMembers()))
                .requests(convertProjectMembers(project, project.getRequests()))
                .build();
    }

    public static ProjectMember makeProjectMember(UserAccount userAccount, Role role) {
        return new ProjectMember(userAccount, role);
    }

    private static ProjectMemberDto makeProjectMemberDto(ProjectMember projectMember, Long projectId) {
        return new ProjectMemberDto(projectId, projectMember.getUserAccount().getUsername(), projectMember.getRole().getRoleString());
    }

    public static ProjectDetailsDto makeProjectDetailsDro(ProjectDto projectDto, List<IssueDto> issues) {
        return new ProjectDetailsDto(projectDto, issues);
    }

    private static Set<ProjectMemberDto> convertProjectMembers(Project project, Set<ProjectMember> members) {
        return members.stream()
                .map(member -> makeProjectMemberDto(member, project.getId()))
                .collect(Collectors.toSet());
    }
}
