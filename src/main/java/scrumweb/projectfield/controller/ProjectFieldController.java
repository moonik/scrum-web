package scrumweb.projectfield.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import scrumweb.dto.projectfield.ProjectFieldDto;
import scrumweb.dto.projectfield.ProjectFieldsCollector;
import scrumweb.projectfield.service.ProjectFieldService;

import java.util.HashSet;
import java.util.Set;

import static scrumweb.common.ApplicationConstants.API_URL;

@RestController
@RequestMapping(API_URL + "project-field")
@AllArgsConstructor
public class ProjectFieldController {

    private ProjectFieldService projectFieldService;
    private ProjectFieldsCollector projectFieldsCollector;

    @PostMapping("")
    public void createProjectField(@RequestParam String issuetype, @RequestParam String projectName,
                                   @RequestBody ProjectFieldsCollector projectFieldsCollector) {
        projectFieldService.createFields(projectFieldsCollector.extractFields(projectFieldsCollector), issuetype, projectName);
    }

    @GetMapping("")
    public Set<ProjectFieldDto> getIssueFields(@RequestParam String issuetype, @RequestParam String projectName) {
        return projectFieldService.getIssueFields(issuetype, projectName);
    }
}
