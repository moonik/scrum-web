package scrumweb.common.asm;

import org.springframework.stereotype.Component;
import scrumweb.dto.issue.IssueDto;
import scrumweb.dto.project.ProjectDetailsDto;
import scrumweb.dto.project.ProjectDto;
import scrumweb.dto.project.ProjectMemberDto;
import scrumweb.user.account.domain.UserAccount;
import scrumweb.project.domain.Project;
import scrumweb.project.domain.ProjectMember.Role;
import scrumweb.project.domain.ProjectMember;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ProjectAsm {
    public Project makeProject(ProjectDto projectDto) {
        return new Project(projectDto.getName(), projectDto.getDescription(), projectDto.getIcon(), projectDto.getProjectKey());
    }

    public ProjectDto makeProjectDto(Project project) {
        return new ProjectDto(project.getId(), project.getName(), project.getDescription(), project.getIcon(),
                project.getMembers().stream()
                        .map(member -> makeProjectMemberDto(member, project.getId()))
                        .collect(Collectors.toSet()), project.getKey());
    }

    public ProjectMember makeProjectMember(UserAccount userAccount, Role role){
        return  new ProjectMember(userAccount, role);
    }

    public ProjectMemberDto makeProjectMemberDto(ProjectMember projectMember, Long projectId){
        return new ProjectMemberDto(projectId, projectMember.getUserAccount().getUsername(),projectMember.getRole().getRoleString());
    }

    public ProjectDetailsDto makeProjectDetailsDro(ProjectDto projectDto, Set<IssueDto> issues) {
        return new ProjectDetailsDto(projectDto, issues);
    }

    public ProjectDto convertFromProjectToProjectDto(Project project){
        Set<ProjectMemberDto> memberDtoSet =
            project.getMembers().stream()
                .map(member -> makeProjectMemberDto(member, project.getId()))
                .collect(Collectors.toSet());
        return new ProjectDto(project.getId(), project.getName(), project.getDescription(),
            project.getIcon(), memberDtoSet, project.getKey());
    }
}
