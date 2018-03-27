package scrumweb.projectfield.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import scrumweb.dto.projectfield.ProjectFieldDto;
import scrumweb.dto.projectfield.ProjectFieldsCollector;
import scrumweb.projectfield.service.ProjectFieldService;

import java.util.Set;

import static scrumweb.common.ApplicationConstants.API_URL;

@RestController
@RequestMapping(API_URL + "project-field")
@AllArgsConstructor
public class ProjectFieldController {

    private ProjectFieldService projectFieldService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    public Set<ProjectFieldDto> createProjectField(@RequestParam String issuetype, @RequestParam String projectKey,
                                   @RequestBody ProjectFieldsCollector projectFieldsCollector) {
        return projectFieldService.createFields(projectFieldsCollector.extractFields(), issuetype, projectKey);
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public Set<ProjectFieldDto> getIssueFields(@RequestParam String issuetype, @RequestParam String projectKey) {
        return projectFieldService.getIssueFields(issuetype, projectKey);
    }

    @DeleteMapping("")
    public Set<ProjectFieldDto> deleteField(@RequestParam Long id, @RequestParam String issuetype, @RequestParam String projectKey) {
        return projectFieldService.removeField(id, projectKey, issuetype);
    }
}