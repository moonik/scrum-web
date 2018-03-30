package scrumweb.project.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import scrumweb.common.SecurityContextService;
import org.springframework.web.bind.annotation.*;
import scrumweb.dto.issue.IssueTypeDto;
import scrumweb.dto.issue.ItemAssignee;
import scrumweb.dto.project.ProjectDetailsDto;
import scrumweb.dto.project.ProjectDto;
import scrumweb.dto.project.ProjectMemberDto;
import scrumweb.dto.search.SearchResultsDto;
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
    public ProjectDto createProject(@RequestBody ProjectDto projectDto) {
        return projectService.create(projectDto);
    }

    @PostMapping("/{projectKey}/members")
    @ResponseStatus(HttpStatus.OK)
    public void addMember(@PathVariable String projectKey, @RequestBody ProjectMemberDto projectMemberDto) {
        projectService.addMember(projectMemberDto, projectKey);
    }

    @DeleteMapping("/{projectKey}/members/{username}")
    @ResponseStatus(HttpStatus.OK)
    public void removeMember(@PathVariable String projectKey, @PathVariable String username) {
        projectService.removeMember(username, projectKey);
    }

    @PutMapping("/{projectKey}")
    @ResponseStatus(HttpStatus.OK)
    public void editProject(@PathVariable String projectKey, @RequestBody ProjectDto projectDto) {
        projectService.editName(projectDto.getName(), projectKey);
    }

    @GetMapping("/details/{projectKey}")
    @ResponseStatus(HttpStatus.OK)
    private ProjectDetailsDto getDetails(@PathVariable String projectKey) {
        return projectService.getProjectDetails(projectKey);
    }

    @GetMapping("/search/{paramkey}")
    @ResponseStatus(HttpStatus.OK)
    public SearchResultsDto findProjectsAndIssuesByKeyQuery(@PathVariable String paramkey) {
        return projectService.findProjectsAndIssuesByKeyQuery(paramkey);
    }

    @GetMapping("/{projectKey}/members")
    @ResponseStatus(HttpStatus.OK)
    public Set<ItemAssignee> getProjectMembers(@PathVariable String projectKey) {
        return projectService.getProjectMembers(projectKey);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<ProjectDto> allProjects() {
        return projectService.findAllProjects();
    }

    @PostMapping("/{projectKey}/access")
    @ResponseStatus(HttpStatus.OK)
    public void askForAccess(@PathVariable String projectKey, @RequestBody ProjectMemberDto projectMemberDto) {
        projectService.askForAccess(projectMemberDto, projectKey);
    }

    @DeleteMapping("{projectKey}/requests/decline/{member}")
    @ResponseStatus(HttpStatus.OK)
    public void declineRequestForAccess(@PathVariable String projectKey, @PathVariable String member) {
        projectService.declineRequestForAccess(projectKey, member);
    }

    @PostMapping("/{projectKey}/requests/accept")
    @ResponseStatus(HttpStatus.OK)
    public void acceptRequestForAccess(@PathVariable String projectKey, @RequestBody ProjectMemberDto projectMemberDto) {
        projectService.addMember(projectMemberDto, projectKey);
    }

    @GetMapping("/{key}/issue-types")
    @ResponseStatus(HttpStatus.OK)
    public List<IssueTypeDto> getIssueTypes(@PathVariable String key) {
        return projectService.getIssueTypes(key);
    }
}
