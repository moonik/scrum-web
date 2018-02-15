package scrumweb.project.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import scrumweb.common.SecurityContextService;
import scrumweb.dto.project.ProjectDto;
import scrumweb.dto.project.ProjectMemberDto;
import scrumweb.project.service.ProjectService;

import java.util.List;

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

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<ProjectDto> allProjects(){
        return projectService.getAllProjects(securityContextService.getCurrentUserAccount());
    }

    @GetMapping("/search/{paramkey}")
    @ResponseStatus(HttpStatus.OK)
    public List<ProjectDto> findProjectsByKeyQuery(@PathVariable String paramkey){
        return projectService.findProjectsByKeyQuery(paramkey);
    }
}
