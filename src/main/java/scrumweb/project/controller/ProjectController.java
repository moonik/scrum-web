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

    @PostMapping("/members/")
    @ResponseStatus(HttpStatus.OK)
    public void addMember(@RequestBody ProjectMemberDto projectMemberDto) {
        projectService.addMember(projectMemberDto);
    }

    @DeleteMapping("{id}/members/{username}")
    @ResponseStatus(HttpStatus.OK)
    public void removeMember(@PathVariable Long id, @PathVariable String username) {
        projectService.removeMember(username, id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void editProject(@PathVariable Long id, @RequestBody ProjectDto projectDto) {
        projectService.editName(projectDto.getName(), id);
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

    @GetMapping("/members/{projectKey}")
    @ResponseStatus(HttpStatus.OK)
    public Set<ItemAssignee> getProjectMembers(@PathVariable String projectKey) {
        return projectService.getProjectMembers(projectKey);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<ProjectDto> allProjects() {
        return projectService.findAllProjects();
    }

    @PostMapping("/access")
    @ResponseStatus(HttpStatus.OK)
    public void askForAccess(@RequestBody ProjectMemberDto projectMemberDto) {
        projectService.askForAccess(projectMemberDto);
    }

    @DeleteMapping("{id}/requests/{member}")
    @ResponseStatus(HttpStatus.OK)
    public void declineRequestForAccess(@PathVariable Long id, @PathVariable String member) {
        projectService.declineRequestForAccess(id, member);
    }

    @PostMapping("/requests")
    @ResponseStatus(HttpStatus.OK)
    public void acceptRequestForAccess(@RequestBody ProjectMemberDto projectMemberDto) {
        projectService.acceptRequestForAccess(projectMemberDto);
    }

    @GetMapping("/{key}/issue-types")
    @ResponseStatus(HttpStatus.OK)
    public List<IssueTypeDto> getIssueTypes(@PathVariable String key) {
        return projectService.getIssueTypes(key);
    }
}
