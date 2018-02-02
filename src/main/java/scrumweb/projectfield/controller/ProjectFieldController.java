package scrumweb.projectfield.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import scrumweb.dto.projectfield.ProjectFieldDto;
import scrumweb.projectfield.service.ProjectFieldService;

import java.util.Set;

import static scrumweb.common.ApplicationConstants.API_URL;

@RestController
@RequestMapping(API_URL + "project-fieldcontent")
@AllArgsConstructor
public class ProjectFieldController {

    private ProjectFieldService projectFieldService;

    @PostMapping("/{issuetype}")
    public void createProjectField(@PathVariable String issuetype, @RequestBody Set<ProjectFieldDto> projectFieldsDto) {
        projectFieldService.createFields(projectFieldsDto, issuetype);
    }

    @GetMapping("/{issuetype}")
    public Set<ProjectFieldDto> getIssueFields(@PathVariable String issuetype) {
        return projectFieldService.getIssueFields(issuetype);
    }
}
