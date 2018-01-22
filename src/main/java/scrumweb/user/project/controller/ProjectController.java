package scrumweb.user.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import scrumweb.dto.ProjectDto;
import scrumweb.user.project.service.ProjectService;

import static scrumweb.common.ApplicationConstants.API_URL;

@RestController
@RequestMapping(API_URL + "profile")
public class ProjectController {

    @Autowired
    protected ProjectService projectService;

    @PostMapping("/createproject")
    @ResponseStatus(HttpStatus.OK)
    public void createProject(@RequestBody ProjectDto projectDto){
        projectService.create(projectDto);
    }

}
