package scrumweb.project.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> addMember(@RequestBody ProjectMemberDto projectMemberDto) {
        return ResponseEntity.status(projectService.addMember(projectMemberDto)).build();
    }

    @DeleteMapping("{id}/members/{username}")
    public ResponseEntity<?> removeMember(@PathVariable Long id, @PathVariable String username) {
        return ResponseEntity.status(projectService.removeMember(username, id)).build();
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

    @GetMapping("/allOwnProjects")
    @ResponseStatus(HttpStatus.OK)
    public List<ProjectDto> allOwnProjects() {
        return projectService.getAllProjects(securityContextService.getCurrentUserAccount());
    }

    @GetMapping("/search/{paramkey}")
    @ResponseStatus(HttpStatus.OK)
    public SearchResultsDto findProjectsAndIssuesByKeyQuery(@PathVariable String paramkey) {
        return projectService.findProjectsAndIssuesByKeyQuery(paramkey);
    }

    @GetMapping("/members/{projectKey}")
    public Set<ItemAssignee> getProjectMembers(@PathVariable String projectKey) {
        return projectService.getProjectMembers(projectKey);
    }

    @PostMapping("/{key}/icon/{filename:.+}")
    public ResponseEntity<?> changeProjectIcon(@PathVariable String key, @PathVariable String filename) {
        return ResponseEntity.status(projectService.changeProjectIcon(filename, key)).build();
    }

    @GetMapping("/allProjects")
    @ResponseStatus(HttpStatus.OK)
    public List<ProjectDto> allProjects() {
        return projectService.findAllProjects();
    }

    @PostMapping("/access")
    public ResponseEntity <?> askForAccess(@RequestBody ProjectMemberDto projectMemberDto) {
        return ResponseEntity.status(projectService.askForAccess(projectMemberDto)).build();
    }

    @DeleteMapping("{id}/requests/{member}")
    public ResponseEntity <?> declineRequestForAccess(@PathVariable Long id, @PathVariable String member) {
        return ResponseEntity.status(projectService.declineRequestForAccess(id, member)).build();
    }

    @PostMapping("/requests")
    public ResponseEntity <?> acceptRequestForAccess(@RequestBody ProjectMemberDto projectMemberDto) {
        return ResponseEntity.status(projectService.acceptRequestForAccess(projectMemberDto)).build();
    }
}
