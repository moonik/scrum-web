package scrumweb.project.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import scrumweb.dto.ProjectDto;
import scrumweb.project.service.ProjectService;

import static scrumweb.common.ApplicationConstants.API_URL;

@RestController
@RequestMapping(API_URL + "project")
@AllArgsConstructor
public class ProjectController {

    protected ProjectService projectService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    public ProjectDto createProject(@RequestBody ProjectDto projectDto){
        return projectService.create(projectDto);
    }

}
