package scrumweb.common.asm;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import scrumweb.dto.issue.IssueDto;
import scrumweb.dto.project.ProjectDetailsDto;
import scrumweb.dto.project.ProjectDto;
import scrumweb.dto.project.ProjectMemberDto;
import scrumweb.project.domain.Project;
import scrumweb.project.domain.ProjectMember;
import scrumweb.project.domain.ProjectMember.Role;
import scrumweb.user.account.domain.UserAccount;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProjectAsm {

    public static Project makeProject(ProjectDto projectDto) {
        return Project.builder()
                .name(projectDto.getName())
                .description(projectDto.getDescription())
                .icon(projectDto.getIcon())
                .key(projectDto.getProjectKey())
                .build();
    }

    public static ProjectDto makeProjectDto(Project project) {
        return ProjectDto.builder()
                .projectKey(project.getKey())
                .name(project.getName())
                .owner(UserProfileAsm.makeUserProfileDto(project.getOwner()))
                .description(project.getDescription())
                .icon(project.getIcon())
                .members(convertProjectMembers(project.getMembers()))
                .requests(convertProjectMembers(project.getRequests()))
                .build();
    }

    public static ProjectMember makeProjectMember(UserAccount userAccount, Role role) {
        return new ProjectMember(userAccount, role);
    }

    private static ProjectMemberDto makeProjectMemberDto(ProjectMember projectMember) {
        return ProjectMemberDto.builder()
                .username(projectMember.getUserAccount().getUsername())
                .role(projectMember.getRole().getRoleString())
                .photo(projectMember.getUserAccount().getUserProfile().getPhoto())
                .build();
    }

    public static ProjectDetailsDto makeProjectDetailsDro(ProjectDto projectDto, List<IssueDto> issues) {
        return ProjectDetailsDto.builder()
                .projectDto(projectDto)
                .issues(issues)
                .build();
    }

    private static Set<ProjectMemberDto> convertProjectMembers(Set<ProjectMember> members) {
        return members.stream()
                .map(ProjectAsm::makeProjectMemberDto)
                .collect(Collectors.toSet());
    }
}
