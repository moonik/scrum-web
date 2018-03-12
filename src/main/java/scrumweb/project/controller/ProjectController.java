package scrumweb.project.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import scrumweb.dto.issue.ItemAssignee;
import scrumweb.dto.project.ProjectDetailsDto;
import scrumweb.common.SecurityContextService;
import scrumweb.dto.project.ProjectDto;
import scrumweb.dto.project.ProjectMemberDto;
import scrumweb.project.service.ProjectService;

import java.util.List;
import java.util.Set;

import static scrumweb.common.ApplicationConstants.API_URL;

@RestController
@RequestMapping(API_URL + "project")
@AllArgsConstructor
public class ProjectController {

    protected ProjectService projectService;
    protected SecurityContextService securityContextService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    public ProjectDto createProject(@RequestBody ProjectDto projectDto){
        return projectService.create(projectDto);
    }

    @PostMapping("/member/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addMember(@RequestBody ProjectMemberDto projectMemberDto){
        projectService.addMember(projectMemberDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProjectDto editProject(@PathVariable Long id,@RequestBody ProjectDto projectDto){
        return projectService.editName(projectDto.getName(), id);
    }

    @GetMapping("/details/{projectKey}")
    @ResponseStatus(HttpStatus.OK)
    private ProjectDetailsDto getDetails(@PathVariable String projectKey) {
        return projectService.getProjectDetails(projectKey);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<ProjectDto> allProjects(){
        return projectService.getAllProjects(securityContextService.getCurrentUserAccount());
    }

    @GetMapping("/members/{projectKey}")
    @ResponseStatus(HttpStatus.OK)
    public Set<ItemAssignee> getProjectMembers(@PathVariable String projectKey) {
        return projectService.getProjectMembers(projectKey);
    }

    @GetMapping("/{key}/issue-types")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getIssueTypes(@PathVariable String key) {
        return projectService.getIssueTypes(key);
    }
}
