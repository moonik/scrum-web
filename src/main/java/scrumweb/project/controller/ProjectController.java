package scrumweb.project.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import scrumweb.common.SecurityContextService;
import scrumweb.dto.issue.ItemAssignee;
import scrumweb.dto.project.ProjectDetailsDto;
import scrumweb.dto.project.ProjectDto;
import scrumweb.dto.project.ProjectMemberDto;
import scrumweb.project.service.ProjectService;

import java.math.BigDecimal;
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
    public ResponseEntity<?> removeMember(@PathVariable Long id,@PathVariable String username) {
        return ResponseEntity.status(projectService.removeMember(username, id)).build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProjectDto editProject(@PathVariable Long id, @RequestBody ProjectDto projectDto) {
        return projectService.editName(projectDto.getName(), id);
    }

    @GetMapping("/details/{projectKey}")
    @ResponseStatus(HttpStatus.OK)
    private ProjectDetailsDto getDetails(@PathVariable String projectKey) {
        return projectService.getProjectDetails(projectKey);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<ProjectDto> allProjects() {
        return projectService.getAllProjects(securityContextService.getCurrentUserAccount());
    }

    @GetMapping("/members/{projectKey}")
    public Set<ItemAssignee> getProjectMembers(@PathVariable String projectKey) {
        return projectService.getProjectMembers(projectKey);
    }

    @PostMapping("/{key}/icon/{filename:.+}")
    public ResponseEntity<?> changeProjectIcon(@PathVariable String key, @PathVariable String filename) {
        return ResponseEntity.status(projectService.changeProjectIcon(filename, key)).build();
    }
}
